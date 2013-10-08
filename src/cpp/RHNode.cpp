#include "RHNode.h"
#include <vector>

bool isOccupied(bool takenSquares[6][6], int x, int y)
{
	if(x > 5 || x < 0 || y > 5 || y < 0)
		return true;

	return takenSquares[y][x];
}

bool isOccupiedByState(gamestate &state, int x, int y)
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
	if(x > 5 || x < 0 || y > 5 || y < 0)
		return true;

	return vehicle.data[0] == 0 ? (vehicle.data[1] < x && vehicle.data[1] + vehicle.data[3] > x) : (vehicle.data[2] < y && vehicle.data[2] + vehicle.data[3] > y);
}

std::vector<gamestate> getSubsequentStates(gamestate &state)
{
	std::vector<gamestate> newStates;
	std::vector<vehicle>::iterator it;

	for(it = state.vehicles.begin(); it != state.vehicles.end(); ++it)
	{	
		if(!it->data[0])
		{
			if(!isOccupied(state.takenSquares, it->data[1]+it->data[3], it->data[2]))
			{
				++it->data[1];
				gamestate newstate;
				newstate = state;
				newStates.push_back(newstate);
				--it->data[1];
			}

			if(!isOccupied(state.takenSquares, it->data[1]-1, it->data[2]))
			{
				--it->data[1];
				gamestate newstate;
				newstate = state;
				newStates.push_back(newstate);
				++it->data[1];
			}
		}
		else
		{
			if(!isOccupied(state.takenSquares, it->data[1], it->data[2]+it->data[3]))
			{
				++it->data[2];
				gamestate newstate;
				newstate = state;
				newStates.push_back(newstate);
				--it->data[2];

			}

			if(!isOccupied(state.takenSquares, it->data[1], it->data[2]-1))
			{
				--it->data[2];
				gamestate newstate;
				newstate = state;
				newStates.push_back(newstate);
				++it->data[2];
			}
		}
	}
	return newStates;
}

bool equals(gamestate& state1, gamestate& state2)
{
	if(state1.vehicles.size() != state2.vehicles.size())
		return false;

	for(unsigned int i = 0; i < state1.vehicles.size(); i++)
	{
		if(state1.vehicles[i].data[1] != state2.vehicles[i].data[1] || state1.vehicles[i].data[2] != state2.vehicles[i].data[2])
			return false;
	}
	return true;
}

RHNode::RHNode(gamestate state)
{
	this->state = state;

	std::vector<vehicle>::iterator it;
	for(int i = 0; i < 6; i++)
	{
		for(int j = 0; j < 6; j++)
		{
			this->state.takenSquares[i][j] = false;
		}
	}

	for(it = this->state.vehicles.begin(); it != this->state.vehicles.end(); ++it)
	{
		vehicle veh = *it;
		int x = veh.data[1];
		int y = veh.data[2];
		int s = veh.data[3];

		if(!veh.data[0])
		{
			this->state.takenSquares[y][x] = true;
			this->state.takenSquares[y][x+1] = true;

			if(s == 3)
				this->state.takenSquares[y][x+2] = true;
		}
		else
		{
			this->state.takenSquares[y][x] = true;
			this->state.takenSquares[y+1][x] = true;

			if(s == 3)
				this->state.takenSquares[y+2][x] = true;
		}
	}
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
		if(isOccupiedByState(state, i, 2) && i != state.vehicles[0].data[1] && i != state.vehicles[0].data[1] + 1)
			++hvalue;
	}
	hvalue *= 2;
	hvalue += 4 - state.vehicles[0].data[1];
	return hvalue;
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

gamestate RHNode::getState() const
{
	return state;	
}

void RHNode::printState()
{
	printf("RHNode state\nf %f g %f", f, g);
	for(int i = 0; i < state.vehicles.size(); i++)
	{
		printf("Vehicle %i\nx %i, y %i, orientation %i, size %i", i, state.vehicles[i].data[1], state.vehicles[i].data[2], state.vehicles[i].data[3]);
	}
}

void RHNode::printMap()
{
	printf("\nMAP:\n--------------\n");
	for(int i = 0; i < 6; i++)
	{
		for(int j = 0; j < 6; j++)
		{
			printf("%s ", this->state.takenSquares[i][j] ? "X" : "O");
		}
		printf("\n");
	}
}
