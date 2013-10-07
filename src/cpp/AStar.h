#pragma once
#include "Node.h"
#include <vector>
class AStar
{
public:
	AStar(void);
	~AStar(void);

	void search(Node &start, std::vector<Node> &results);

	//Helper functions
	void backTracePath(std::vector<Node> &results, Node &end);
	void contains(std::vector<Node> &vec, Node &element);
};

