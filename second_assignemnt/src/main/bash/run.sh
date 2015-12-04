#Designed to be run from src/main/java where src is in the root directory of the project
DIR="`pwd`"

BIN=../../../bin

cd $BIN
java Main

for FILE in *Test.class; do
	file="$(echo -e "${FILE}" | sed -e 's/.class$//')"
 java org.junit.runner.JUnitCore $file
done

{
cd $DIR
} &> /dev/null
