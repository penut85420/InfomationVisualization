var parseTime = d3.timeParse("%m/%d");
var svg, margin, width, height;
var x, y, g;

var TeamID = {brother: "E02", lamigo: "A02", fubon: "B04", lion: "L01"};

function change(id, type) {
	if (type == undefined) type = document.getElementById("type").value;
	d3.tsv("data/FollowPitch/FollowPitch" + id + ".tsv", function(d) {
		d.date = parseTime(d.DATE);
		d.data = d[type];
		return d;
	}, function(error, data) {
		if (error) throw error;

		svg.selectAll("#child").remove();

		x.domain(d3.extent(data, function(d) { return d.date; }));
		y.domain([0, d3.max(data, function(d) { return parseInt(d.BF); })]);

		g.append("g")
			.attr("id", "child")
			.attr("class", "axis axis--x")
			.attr("transform", "translate(0," + height + ")")
			.call(d3.axisBottom(x))
			.select(".domain");

		g.append("g")
			.attr("id", "child")
			.attr("class", "axis axis--y")
			.call(d3.axisLeft(y))
			.append("text")
			.attr("transform", "rotate(-90)")
			.attr("y", 6)
			.attr("dy", "0.71em")
			.attr("text-anchor", "end")
			.text("H");

		g.selectAll(".bar2")
			.data(data)
			.enter().append("rect")
			.attr("id", "child")
			.attr("class", "bar2")
			.attr("x", function(d) { return x(d.date); })
			.attr("y", function(d) { return y(d.BF); })
			.attr("width", 10)
			.attr("height", function(d) { return height - y(d.BF); });

		g.selectAll(".bar")
			.data(data)
			.enter().append("rect")
			.attr("id", "child")
			.attr("class", "bar")
			.attr("x", function(d) { return x(d.date); })
			.attr("y", function(d) { return y(d.data); })
			.attr("width", 10)
			.attr("height", function(d) { return height - y(d.data); });
	});
}

function changeType(value) {
	var t = document.getElementById("member").value;
	change(t, value);
}

$(document).ready(function() {
	svg = d3.select("svg"),
	margin = {top: 20, right: 20, bottom: 30, left: 40},
	width = +svg.attr("width") - margin.left - margin.right,
	height = +svg.attr("height") - margin.top - margin.bottom;

	x = d3.scaleTime().rangeRound([0, width]),
	y = d3.scaleLinear().rangeRound([height, 0]);

	g = svg.append("g")
		.attr("transform", "translate(" + margin.left + "," + margin.top + ")");
	
	changeTeam(document.getElementById("team").value, "Pitcher");
});