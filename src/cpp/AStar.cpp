#include "AStar.h"
#include <deque>
#include <algorithm>


AStar::AStar(void)
{

}

AStar::~AStar(void)
{

}

void AStar::search(RHNode &start, std::vector<RHNode> &results)
{
	std::vector<RHNode> closedSet;
	std::vector<RHNode> openSet;

	start.g = 0;
	start.f = start.g + start.calculateHeuristic();

	printf("Heuristic: %f", start.calculateHeuristic());

	openSet.push_back(start);
	RHNode lol = openSet.back();
	lol.printState();

	while(openSet.size() != 0)
	{
		RHNode current = openSet.back();
		openSet.pop_back();
		closedSet.push_back(current);

		if(current.isSolution())
		{
			backTracePath(results, start, current);
			break;
		}

		current.expand();
		current.printMap();
		for(unsigned int i = 0; i < current.kids.size(); i++)
		{	
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
				
				current.kids[i].parent = &current;
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

void AStar::backTracePath(std::vector<RHNode> &results, RHNode &start, RHNode &end)
{
	RHNode prev, cur;
	cur = end;
	while(!(start == cur))
	{
		cur = *prev.parent;
		results.push_back(cur);
		prev = cur;
	}
}

int contains(std::vector<RHNode> &vec, RHNode &element)
{
	std::vector<RHNode>::iterator it = std::find(vec.begin(), vec.end(), element);	
	if(it == vec.end())
		return -1;
	return it - vec.begin();
}


bool remove(std::vector<RHNode> &vec, RHNode &element)
{
	std::vector<RHNode>::iterator it = std::find(vec.begin(), vec.end(), element);
	if(it == vec.end())
		return true;
	vec.erase(it);
	return true;
}
