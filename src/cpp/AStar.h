#pragma once
#include "RHNode.h"
#include <vector>

class Node;

int contains(std::vector<RHNode> &vec, RHNode &element);
bool remove(std::vector<RHNode> &vec, RHNode &element);

class AStar
{
public:
	AStar(void);
	~AStar(void);

	void search(RHNode &start, std::vector<RHNode> &results);

	//Helper functions
	void backTracePath(std::vector<RHNode> &results, RHNode &start, RHNode &end);
};

