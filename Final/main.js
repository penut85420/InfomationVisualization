var parseTime = d3.timeParse("%m/%d");

var svg, margin, width, height, g, x, y, line;



function change(value) {
	svg.selectAll("#C").remove();

	d3.tsv("data/FollowHit/FollowHit" + value + ".tsv", function(d) {
		d.date = parseTime(d.DATE);
		d.close = +d.AVG;
		return d;
	}, function(error, data) {
		if (error) {
			console.log(error);
			throw error;
		}

		x.domain(d3.extent(data, function(d) { return d.date; }));
		y.domain([0, 0.6]);
		
		g.append("g")
			.attr("id", "C")
			.attr("transform", "translate(0," + height + ")")
			.call(d3.axisBottom(x))
			.select(".domain");

		g.append("g")
			.attr("id", "C")
			.call(d3.axisLeft(y))
			.append("text")
			.attr("fill", "#000")
			.attr("transform", "rotate(-90)")
			.attr("y", 6)
			.attr("dy", "0.71em")
			.attr("text-anchor", "end")
			.text("AVG");

		g.append("path")
			.datum(data)
			.attr("id", "C")
			.attr("fill", "none")
			.attr("stroke", "steelblue")
			.attr("stroke-linejoin", "round")
			.attr("stroke-linecap", "round")
			.attr("stroke-width", 1.5)
			.attr("d", line);
	});
}

$(document).ready(function() {
	svg = d3.select("svg"),
	margin = {top: 20, right: 20, bottom: 30, left: 50},
	width = +svg.attr("width") - margin.left - margin.right,
	height = +svg.attr("height") - margin.top - margin.bottom,
	g = svg.append("g").attr("transform", "translate(" + margin.left + "," + margin.top + ")");

	x = d3.scaleTime().rangeRound([0, width]);
	y = d3.scaleLinear().rangeRound([height, 0]);
	line = d3.line()
		.x(function(d) { return x(d.date); })
		.y(function(d) { return y(d.close); });

	d3.tsv("data/PlayerHitterID.tsv", (d)=>{
		var mem = document.getElementById("member");
		for (var i in d) {
			
			var option = document.createElement("option");
			option.value = d[i].ID;
			option.text = d[i].NAME;
			mem.add(option);
		}
		change(d[0].ID);
	});

	// change(document.getElementById("member").value);
});

