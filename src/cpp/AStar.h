#pragma once
#include "RHNode.h"
#include <deque>

class Node;

int contains(std::deque<RHNode> &vec, RHNode &element);
bool remove(std::deque<RHNode> &vec, RHNode &element);

class AStar
{
public:
	AStar(void);
	~AStar(void);

	void search(RHNode &start, std::vector<RHNode*> &results);

	//Helper functions
	void backTracePath(std::vector<RHNode*> &results, RHNode &start, RHNode &end);
};

