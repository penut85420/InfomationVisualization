<!DOCTYPE html>
<meta charset="utf-8">
<style>

path {
  fill: #ccc;
  stroke: #333;
  stroke-width: 1.5px;
  transition: fill 250ms linear;
  transition-delay: 150ms;
}

path:hover {
  fill: #999;
  stroke: #000;
  transition-delay: 0;
}

</style>
<body>
<script src="//d3js.org/d3.v3.min.js"></script>
<script src="//d3js.org/d3.v4.min.js"></script>
<script>

var data = [8, 13, 21, 34, 55, 89];

	var width = 960,
		height = 500;

	var outerRadius = height / 2 - 20,
		innerRadius = outerRadius / 3,
		cornerRadius = 50,
		delay = 150;

	var pie = d3.layout.pie()
		.padAngle(.02);

	var label = d3.arc()
    .outerRadius(outerRadius - 40)
    .innerRadius(outerRadius - 40);

	var arc = d3.svg.arc()
		.padRadius(outerRadius)
		.innerRadius(innerRadius);

	var svg = d3.select("body").append("svg")
		.attr("width", width)
		.attr("height", height)
		.append("g")
		.attr("transform", "translate(" + width / 2 + "," + height / 2 + ")");

	svg.selectAll("path")
		.data(pie(data))
		.enter().append("path")
		.each(function(d) { d.outerRadius = outerRadius - cornerRadius; })
		.attr("d", arc)
		.on("mouseover", arcTween(outerRadius, 0))
		.on("mouseout", arcTween(outerRadius - cornerRadius, delay));

	function arcTween(outerRadius, delay) {
		return function() {
			d3.select(this).transition().delay(delay).attrTween("d", function(d) {
				var i = d3.interpolate(d.outerRadius, outerRadius);
				return function(t) { d.outerRadius = i(t); return arc(d); };
			});
		};
	}

</script>