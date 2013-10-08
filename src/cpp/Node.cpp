#include "Node.h"


Node::Node(void)
{

}

Node::~Node(void)
{

}

void Node::expand(){

}

int Node::calculateHeuristic(){
	return 1;
}

void Node::calculateValues(){
	g = parent->g + 1;
	f = g + calculateHeuristic();
}

bool Node::isSolution(){
	return false;
}





