cd ../../../bin/
FILES=*Test.class
for f in $FILES
do
	java org.junit.runner.JUnitCore 
done
cd -
