import std.stdio;
import std.random;
import std.math;
import std.array;
import std.datetime;

immutable int size = 1000;

bool[size][size] kqueens;
/*
 * Main algorithm
 */
void main() {
    // Generate board
    genBoard();

    // Starting timer
    auto start = Clock.currTime();

    // Perform local-search
    ls();
    
    // Stopping timer
    auto stop = Clock.currTime();



    // Printing result
    printBoard();
    writeln("Time elapsed: ", stop-start);
    writeln("Board done? ", isDone());

}

/*
 * The Local Search algorithm
 */
void ls() {
    // Declaring variables
    int row, min_value;
    ulong rnd_pick;
    int[] min_indexes;
    int[size] conflicts;

    // Will randomly pick 1000000 rows before exit
    for(int i=0; i<10000000; ++i) {
        
        // Checking board status every 100 iteration
        if(i%100 == 0) {
            if(isDone()) {
                return;
            }
        }
        
        // Finding random row
        row = randInt();

        // Generating conflicts row
        conflicts = getConflictsRow(row);
        min_value = conflicts[0];

        // Iterating through conflicts row
        for(int j=0; j<size; ++j) {
            // If we found an index with a smaller value than previously saved, clear the min_indexes array and 
            if(conflicts[j] < min_value) {
                clear(min_indexes);
                min_value = conflicts[j];
            }
            // If we found an equal value to the minimum value, add it to the min_indexes array
            if(conflicts[j] == min_value) {
                min_indexes ~= j;
            }
        }
        // Picking random of minimum indexes and setting this row
        rnd_pick = uniform(0, min_indexes.length);
        if(!kqueens[row][min_indexes[rnd_pick]]){
            setRow(row, min_indexes[rnd_pick]);
        }
        // Clearing min_indexes array so its ready for next round
        clear(min_indexes);

    }
}
/*
 * Setting a row to have a queen at a certain value
 */
void setRow(int row, int column) {
    for(int i=0; i<size;++i) {
        kqueens[row][i] = i==column;
    }
}

/*
 * Returns random int based on board size
 */
int randInt() {
    return uniform(0, size);
}
/*
 * Generates random board. Iterates through all rows and adds a queen at random position
 */
void genBoard() {
    for(int i=0; i<size; ++i) {
        kqueens[i][randInt()] = true;
    }
}

/*
 * Prints the board
 */
void printBoard(){
    string str = "";
    for(int i=0; i<size; ++i) {
        if(i!=0) {
            str ~= "\n";
        }
        for(int j=0; j<size; ++j) {
            str ~= kqueens[i][j]?" X ":" O ";
        }
    }
    writeln(str);
}

/*
 * Returns an array (with board size) with the number of conflicts on each column at the given row.
 */
int[size] getConflictsRow(int row) {
    int[size] res;
    for(int i=0; i<size;++i) {
        res[i] = getConflicts(row, i);
    }
    return res;
}

/*
 * Returns the number of conflicts at a position
 */
int getConflicts(int row, int column) {
    int conflicts = 0;
    int delta;
    for(int i=0;i<size;i++) {
        // Skip current row
        if(i==row) {
            continue;
        }
        delta = abs(i-row);

        // Bounds check
        if(column-delta >=0) {
            conflicts += kqueens[i][column-delta]?1:0;
        }
        if(column+delta < size) {
            conflicts += kqueens[i][column+delta]?1:0;
        }
        conflicts += kqueens[i][column]?1:0;
    }
    return conflicts;
}
/*
 * Checks if board is done.
 * These methods are more efficient than the integer versions, as these return immideatly upon conflict
 */
bool isDone() {
    for(int i=0; i<size; ++i) {
        for(int j=0; j<size; ++j) {
            if(hasConflict(i,j)) { 
                // Writing output to see status
                writeln("CONFLICT AT ", i,"x",j);
                return false; 
            }
        }
    }
    return true;
}
/*
 * Checks if a position has a conflict.
 * These methods are more efficient than the integer versions, as these return immideatly upon conflict.
 */
bool hasConflict(int row, int column) {
    int delta;
    if(!kqueens[row][column]) {return false;}

    for(int i=0; i<size; ++i) {
        if(i==row) { continue; }
        delta = abs(i-row);
        if(column-delta >= 0) {
            if(kqueens[i][column-delta]) { return true; }
        }
        if(column+delta < size) {
            if(kqueens[i][column+delta]) { return true; }
        }
        if(kqueens[i][column]) { return true; }
    }
    return false;
}
