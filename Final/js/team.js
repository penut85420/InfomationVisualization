var svg, width, height, outerRadius, innerRadius, g;
var pie, path, arc, label;
var bgcolor = {
	Lamigo: "#0F74A0",
	中信兄弟: "#F8C715",
	"統一7-ELEVEn": "#D87728",
	"富邦": "#F1F1F3"
};

function change(type) {
	var unit = type.split(";")[1];
	type = type.split(";")[0];
	pie = d3.pie()
		.sort(null)
		.value(function(d) { return parseInt(d[type]); })
		.padAngle(.02);

	d3.tsv("data/TeamStanding.tsv", (d) => {
		return d;
	}, (err, data) => {
		svg.selectAll("#node").remove();
		
		arc = g.selectAll(".arc")
			.data(pie(data))
			.enter().append("g")
			.attr("id", "node")
			.attr("class", "arc");

		arc.append("path")
			.attr("id", "node")
			.attr("d", path)
			.attr("fill", (d)=>{ return bgcolor[d.data.TEAM]; });

		arc.append("text")
			.attr("id", "node")
			.attr("transform", function(d) { return "translate(" + label.centroid(d) + ")"; })
			.attr("dy", "0.35em")
			.text(function(d) { return d.data.TEAM + " " + d.data[type] + unit; });
	});
}

$(document).ready(function() {
	svg = d3.select("svg");
	width = +svg.attr("width");
	height = +svg.attr("height");
	outerRadius = height / 2 - 20;
	innerRadius = outerRadius / 3;
	g = svg.append("g").attr("transform", "translate(" + width / 2 + "," + height / 2 + ")");

	path = d3.arc()
		.outerRadius(outerRadius)
		.innerRadius(innerRadius);

	label = d3.arc()
		.outerRadius(outerRadius - 40)
		.innerRadius(outerRadius - 40);
	
	change(document.getElementById("type").value);
});