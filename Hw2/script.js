var dateArr = {};
var dateObj = [];
var stations;
var svg;
var color = d3.scale.linear().domain([0,10000]).range(["#090","#f00"]);
var nowData;
var path = function(d, r) {
		return d3.geo.path().projection(
			d3.geo.mercator().center([122,23.25]).scale(6000)
		).pointRadius(r)(d);
	}

$(document).ready(function() {
	svg = d3.select("#tw");
	drawTw();
	d3.json("stations.json", function(topodata) {
		stations = topojson.feature(topodata, topodata.objects.stations).features;
		d3.csv("data.csv", function(d) {
			dateArr[d.date] = d.date;
			for (var i in stations) {
				if (stations[i].properties.landmarkna.includes(d.name)) {
					if (stations[i].date == undefined)
						stations[i].date = {};
					stations[i].date[d.date] = {};
					stations[i].date[d.date].in = d.in;
					stations[i].date[d.date].out = d.out;
				}
				continue;
			}
			return d;
		}, function(error, data) {
			if (error) throw error;
			j = 0;
			for (var i in dateArr)
				dateObj[j++] = dateArr[i];
			console.log(dateObj);
			svg.selectAll("rail").data(stations).enter().append("path");
			update("0");
		});
	});
});

function update(value) {
	svg.selectAll("path").selectAll("#dot").remove();
	document.getElementById("showDate").innerHTML = dateObj[value];
	value = dateObj[value];
	draw(value);
	if (nowData != undefined)
		displayInfo(nowData, value);
}

function getValue(d, value) {
	return (parseInt(d.date[value].in) + parseInt(d.date[value].out)) / 1500;
}

function draw(value) {
	svg.selectAll("path")
		.attr("id", "dot")
		.attr("fill", (d) => { 
			if (d.date != undefined)
				if (d.date[value] != undefined)
					return color(getValue(d, value));
			if (d.properties.C_Name != undefined) return "AntiqueWhite ";
			return color(10);
		})
		.attr("opacity", (d) => {return d.properties.C_Name == undefined? 0.1: 1})
		.attr("d", (d)=>{
			if (d.date != undefined)
				if (d.date[value] != undefined)
					return path(d, getValue(d, value));
			return path(d, 2);
		})
		.on("mouseover", function(d) {
			nowData = d;
			console.log(nowData);
			displayInfo(d, value);
			d3.select(this).attr("fill", (d) => { 
				if (d.properties.C_Name != undefined) return "AntiqueWhite ";
				return "blue";
			}).attr("opacity", (d) => {return d.properties.C_Name == undefined? 0.5: 1});
		})
		.on("mouseout", function(d) {
			d3.select(this).attr("fill", (d) => { 
				if (d.properties.C_Name != undefined) return "AntiqueWhite ";
				if (d.date != undefined)
					if (d.date[value] != undefined)
						return color(getValue(d, value));
				return color(10);
			}).attr("opacity", (d) => {return d.properties.C_Name == undefined? 0.1: 1})
		});
}

function displayInfo(d, value) {
	if (d.date != undefined) {
		$("#name").text(d.properties.landmarkna);
		$("#in").text(d.date[value].in);
		$("#out").text(d.date[value].out);
	}
}

function drawTw() {
	d3.json("country.json", function(topodata) {
		var features = topojson.feature(topodata, topodata.objects.county).features;
		var path = d3.geo.path().projection(
			d3.geo.mercator().center([122,23.25]).scale(6000)
		);

		svg.selectAll("tw").data(features)
			.enter().append("path").attr({
				"d": path,
				"fill": "AntiqueWhite ",
			});
	});
}