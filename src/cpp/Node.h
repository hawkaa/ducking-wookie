#pragma once

#include <vector>

class Node
{
public:
	Node(void);
	~Node(void);

	float f, g, h;

	virtual void expand();
	virtual int calculateHeuristic();
	virtual void calculateValues();
	virtual bool isSolution();

	virtual bool operator==(const Node& other)const{return true;};

	bool isOpen();

	Node* parent;
	std::vector<Node> kids;
	
	bool open;
};

