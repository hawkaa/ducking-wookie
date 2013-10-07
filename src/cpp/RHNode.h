#pragma once
#include "node.h"
#include <vector>

struct vehicle
{
	int data[4];
};

struct gamestate 
{
	int size;
	std::vector<vehicle> vehicles;
	static const int exit_x = 5, exit_y = 2;
};

std::vector<gamestate> getSubsequentStates(gamestate &state);
bool isOccupied(gamestate &state, int x, int y);
bool occupies(vehicle &vehicle, int x, int y);
bool equals(gamestate& state1, gamestate& state2);

class RHNode : public Node
{
public:
	RHNode(gamestate state);
	~RHNode(void);

	//Required for std::find, see AStar.cpp
	bool operator==(RHNode& node){return equals(this->getState(), node.getState());};
	
	void expand();
	int calculateHeuristic();
	void calculateValues();
	bool isSolution();
	gamestate getState();

private:
	gamestate state;
};

