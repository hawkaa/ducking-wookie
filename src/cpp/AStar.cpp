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
	std::deque<Node> closedSet;
	std::deque<Node> openSet;

	openSet.push_front(start);
	
	bool found = false;

	while(!found)
	{
		Node current = openSet[0];
		openSet.pop_front();
		closedSet.push_front(current);

		if(current.isSolution())
			break;

		current.expand();

		for(int i = 0; i < current.kids.size(); i++)
		{
			
		}
	}
}


//Helper function
void AStar::contains(std::vector<Node> &vec, Node &element)
{

}