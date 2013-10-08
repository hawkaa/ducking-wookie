#include <stdio.h>
#include "AStar.h"
#include "Node.h"
#include "RHNode.h"

vehicle& addVehicleData( int d0, int d1, int d2, int d3){
	vehicle* vehic = new vehicle();
	vehic->data[0] = d0;
	vehic->data[1] = d1;
	vehic->data[2] = d2;
	vehic->data[3] = d3;
	return *vehic;
}

void initTest1(gamestate& start){
	start.size = 6;
	start.vehicles.reserve(6);
	start.vehicles.push_back(addVehicleData(0, 2, 2, 2));
	start.vehicles.push_back(addVehicleData(0, 0, 4, 3));
	start.vehicles.push_back(addVehicleData(0, 3, 4, 2));
	start.vehicles.push_back(addVehicleData(0, 4, 1, 2));
	start.vehicles.push_back(addVehicleData(1, 2, 0, 2));
	start.vehicles.push_back(addVehicleData(1, 4, 2, 2));
}

void initTest2(gamestate& start){

}

void initTest3(gamestate& start){

}

void initTest4(gamestate& start){

}



int main(int argc, char** argv)
{
	AStar search;
	std::vector<RHNode*> results;

	gamestate start1;
	initTest1(start1);

	RHNode startNode1(start1);
	search.search(startNode1, results);

	printf("\nSuccess!\n");
	printf("\nSolution found with: \n");
	printf("%i moves", results.size());

}

