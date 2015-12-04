#Designed to be run from src/main/java where src is in the root dir of the project
DIR_ROOT=../../../

TEST=../../test

BIN_SRC=$DIR_ROOT/bin/src
BIN_TEST=$DIR_ROOT/bin/test
BIN=$DIR_ROOT/bin

if [ ! -d $DIR_ROOT/bin ]; then
#	mkdir -p $DIR_ROOT/bin/src/
#	mkdir -p $DIR_ROOT/bin/test/
	mkdir -p $BIN
fi

javac *.java -d $BIN #$BIN_SRC

if [ -d $TEST ]; then
	javac $TEST/*.java -d $BIN #$BIN_TEST
fi
