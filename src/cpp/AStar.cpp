#include "AStar.h"
#include <deque>

AStar::AStar(void)
{

}


AStar::~AStar(void)
{

}

void AStar::search(Node &start, std::vector<Node> &results)
{
	std::vector<Node> closedSet;
	std::vector<Node> openSet;

	start.g = 0;
	start.f = start.g + start.calculateHeuristic();

	openSet.push_back(start);
	
	bool found = false;

	while(!found)
	{
		Node current = *openSet.end();
		openSet.pop_back();
		closedSet.push_back(current);

		if(current.isSolution())
			break;

		current.expand();

		for(int i = 0; i < current.kids.size(); i++)
		{
			int t_g = current.g + 1;
			int t_f = t_g + current.kids[i].calculateHeuristic();
			
			int id = contains(closedSet, current.kids[i]);

			if(containsAndIsSmallerThan(closedSet, current.kids[i]))
				continue;


		}
	}
}


//Helper functions
bool containsAndIsSmallerThan(std::vector<Node> &vec, Node &element)
{

}

int contains(std::vector<Node> &vec, Node &element)
{
	std::vector<Node>::iterator it = std::find(vec.begin(), vec.end(), element);	
}