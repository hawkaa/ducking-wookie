import std.stdio;
import std.random;
import std.math;
import std.array;

immutable int size = 100;

bool[size][size] kqueens;

void main() {
    genBoard();
    //printBoard();
    //writeln(getConflictsRow(0));
    //writeln(getConflictsRow(2));
    //ls();
    setRow(0,0);
    setRow(1,1);
    ls();
    //printBoard();
    writeln(isDone());
}

void ls() {
    int row, min_value;
    int[] min_indexes;
    int[size] conflicts;
    for(int i=0; i<100000; ++i) {
        row = randInt();
        conflicts = getConflictsRow(row);
        //writeln("Conflicts on row ", row, ": ", conflicts);
        min_value = conflicts[0];
        for(int j=0; j<size; ++j) {
            if(conflicts[j] < min_value) {
                clear(min_indexes);
                min_value = conflicts[j];
            }
            if(conflicts[j] == min_value) {
                min_indexes ~= j;
            }
        }
       // writeln(min_indexes);
        setRow(row, min_indexes[uniform(0, min_indexes.length)]);
        clear(min_indexes);
            

        //printBoard();

    }
}

void setRow(int row, int column) {
    for(int i=0; i<size;++i) {
        kqueens[row][i] = i==column;
    }
}


int randInt() {
    return uniform(0, size);
}

void genBoard() {
    for(int i=0; i<size; ++i) {
        kqueens[i][randInt()] = true;
    }
}


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

int[size] getConflictsRow(int row) {
    int[size] res;
    for(int i=0; i<size;++i) {
        res[i] = getConflicts(row, i);
    }
    return res;
}

int getConflicts(int row, int column) {
    int conflicts = 0;
    int delta;
    for(int i=0;i<size;i++) {
        if(i==row) {
            continue;
        }
        delta = abs(i-row);
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

bool isDone() {
    for(int i=0; i<size; ++i) {
        for(int j=0; j<size; ++j) {
            if(hasConflict(i,j)) { 
                writeln("CONFLICT AT ", i,"x",j);
                return false; 
            }
        }
    }
    return true;
}

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
