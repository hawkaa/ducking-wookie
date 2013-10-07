#include "RHNode.h"
#include <vector>

bool isOccupied(gamestate &state, int x, int y)
{
	std::vector<vehicle>::iterator it;

	for(it = state.vehicles.begin(); it != state.vehicles.end(); ++it)
	{
		if(occupies(*it, x, y))
			return true;
	}
	return false;
}

bool occupies(vehicle &vehicle, int x, int y)
{
	if(x > 6 || x < 0 || y > 6 || y < 0)
		return true;

	return vehicle.data[0] == 0 ? (vehicle.data[1] < x && vehicle.data[1] + vehicle.data[3] > x) : (vehicle.data[2] < y && vehicle.data[2] + vehicle.data[3] > y);
}

std::vector<gamestate> getSubsequentStates(gamestate &state)
{
	std::vector<gamestate> newStates;
	std::vector<vehicle>::iterator it;

	for(it = state.vehicles.begin(); it != state.vehicles.end(); ++it)
	{
		if(it->data[0])
		{
			if(!isOccupied(state, it->data[1]+1, it->data[2]))
			{
				++it->data[1];
				gamestate newstate;
				memcpy(&newstate, &state, sizeof(gamestate));
				--it->data[1];
				newStates.push_back(newstate);
			}

			if(!isOccupied(state, it->data[1]-1, it->data[2]))
			{
				++it->data[1];
				gamestate newstate;
				memcpy(&newstate, &state, sizeof(gamestate));
				--it->data[1];
				newStates.push_back(newstate);
			}
		}
		else
		{
			if(!isOccupied(state, it->data[1], it->data[2]+1))
			{
				++it->data[2];
				gamestate newstate;
				memcpy(&newstate, &state, sizeof(gamestate));
				--it->data[2];
				newStates.push_back(newstate);
			}

			if(!isOccupied(state, it->data[1], it->data[2]-1))
			{
				--it->data[2];
				gamestate newstate;
				memcpy(&newstate, &state, sizeof(gamestate));
				++it->data[2];
				newStates.push_back(newstate);
			}
		}
	}
	return newStates;
}

bool equals(gamestate& state1, gamestate& state2)
{
	if(state1.vehicles.size() != state2.vehicles.size())
		return false;
	for(int i = 0; i < state1.vehicles.size(); i++)
	{
		if(state1.vehicles[i].data[1] != state2.vehicles[i].data[1] || state1.vehicles[i].data[2] != state2.vehicles[i].data[2])
			return false;
	}
	return true;
}

RHNode::RHNode(gamestate state)
{
	this->state = state;
}


RHNode::~RHNode(void)
{

}

void RHNode::expand()
{
	std::vector<gamestate> subGameStates = getSubsequentStates(this->state);
	std::vector<gamestate>::iterator it;

	for(it = subGameStates.begin(); it != subGameStates.end(); ++it)
	{
		kids.push_back(RHNode(*it));
	}
}


int RHNode::calculateHeuristic()
{
	int hvalue = 0;
	for(int i = 0; i < 6; i++)
	{
		if(isOccupied(state, i, 2) && i != state.vehicles[0].data[1] && i != state.vehicles[0].data[1] + 1)
			++hvalue;
	}
	hvalue += 4 - state.vehicles[0].data[1];
}

void RHNode::calculateValues()
{
	g = parent->g + 1;
	f = g + this->calculateHeuristic();
}

bool RHNode::isSolution()
{
	if(state.vehicles[0].data[1] == 4)
		return true;
	return false;
}

gamestate RHNode::getState()
{
	
}
