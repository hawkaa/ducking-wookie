#include "AStar.h"
#include <deque>
#include <algorithm>


AStar::AStar(void)
{

}

AStar::~AStar(void)
{

}

void AStar::search(RHNode &start, std::vector<RHNode*> &results)
{
	std::deque<RHNode> closedSet;
	std::deque<RHNode> openSet;

	start.g = 0;
	start.f = start.g + start.calculateHeuristic();

	openSet.push_front(start);
	
	while(openSet.size() != 0)
	{
		RHNode current = openSet.front();
		openSet.pop_front();
		closedSet.push_back(current);

		if(current.isSolution())
		{
			backTracePath(results, start, current);
			return;
		}

		current.expand();
		for(unsigned int i = 0; i < current.kids.size(); i++)
		{	
			current.kids[i].parent = new RHNode();
			memcpy(current.kids[i].parent, &current, sizeof(RHNode));
			int t_g = current.g + 1;
			int t_f = t_g + current.kids[i].calculateHeuristic();
			
			int id = contains(closedSet, current.kids[i]);

			int o_f = id != -1 ? closedSet[id].f : 0;

			if(id != -1 && t_f >= o_f)
				continue;
			
			if(contains(openSet, current.kids[i]) == -1 || t_f < o_f)
			{
				if(contains(closedSet, current.kids[i]) != -1)
					remove(closedSet, current.kids[i]);
				
				current.kids[i].calculateValues();
				if(contains(openSet, current.kids[i]) == -1)
				{
					openSet.push_back(current.kids[i]);
					std::sort(openSet.begin(), openSet.end());
				}
			}
		}
	}
}

// very hacky
void AStar::backTracePath(std::vector<RHNode*> &results, RHNode& start, RHNode& end)
{
	RHNode* cur = &end;
	RHNode* other = &end;
	int i = 0;
	while(cur->parent != nullptr)
	{	
		++i;
		cur = other;
		//results.push_back(*cur);
		other = cur->parent;
	}

	RHNode** references = new RHNode*[i];

	cur = &end;
	other = &end;
	for(int j = i-1; j > 0; --j)
	{
		cur = other;
		references[j] = cur;
		other = cur->parent;
	}
	RHNode* hey = references[i-1];

	for(int k = 0; k < i; ++k)
	{
		results.push_back(references[k]);
	}
}

int contains(std::deque<RHNode> &vec, RHNode &element)
{
	std::deque<RHNode>::iterator it = std::find(vec.begin(), vec.end(), element);	
	if(it == vec.end())
		return -1;
	return it - vec.begin();
}


bool remove(std::deque<RHNode> &vec, RHNode &element)
{
	std::deque<RHNode>::iterator it = std::find(vec.begin(), vec.end(), element);
	if(it == vec.end())
		return true;
	vec.erase(it);
	return true;
}
