function changeTeam(team, type) {
	console.log("data/Player" + type + TeamID[team] + "ID.txt");
	d3.tsv("data/Player" + type + TeamID[team] + "ID.txt", (d)=>{
		var mem = document.getElementById("member");
		var name = [],
			ele = {},
			id = {};
		
		for (var i in d) {
			var option = document.createElement("option");
			if (d[i].ID != undefined) {
				option.value = d[i].ID;
				option.text = d[i].NAME;
				ele[d[i].NAME] = option;
				name.push(d[i].NAME);
				id[d[i].NAME] = d[i].ID;
			}
		}
		
		name.sort();
		
		for (var i in mem)
			mem.remove(i);

		for (var i in name)
			mem.add(ele[name[i]]);

		change(id[name[0]]);
	});
}