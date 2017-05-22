function foo1() {
	// do something...
}

function foo2() {
	// do foo2;
	for (var i = 0; i < 10; i++)
		foo1();
}

function foo3() {
	foo2();
	// do foo3
}