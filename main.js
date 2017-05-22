function foo1() {
	// do something...
}

function foo2() {
	// do foo2;
	foo1();
}

function foo3() {
	foo2();
	// do foo3
}