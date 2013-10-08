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
	bool takenSquares[6][6];
	std::vector<vehicle> vehicles;
	static const int exit_x = 5, exit_y = 2;

};

std::vector<gamestate> getSubsequentStates(gamestate &state);
bool isOccupied(bool takenSquares[6][6], int x, int y);
bool isOccupiedByState(gamestate &state, int x, int y);
bool occupies(vehicle &vehicle, int x, int y);
bool equals(gamestate& state1, gamestate& state2);

class RHNode
{
public:

	RHNode(void){};
	RHNode(const gamestate state);
	~RHNode(void);

	//Required for std::find, see AStar.cpp
	bool operator==(const RHNode& node)const{return equals(getState(), node.getState());};
	
	void expand();
	int calculateHeuristic();
	void calculateValues();
	bool isSolution();
	gamestate getState() const;

	float f, g, h;
	

	void printState();
	RHNode* parent;
	std::vector<RHNode> kids;

private:
	gamestate state;
};

