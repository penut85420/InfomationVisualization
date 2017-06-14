<!DOCTYPE html>
<meta charset="utf-8">
<?php include("head/head.php"); ?>
<link rel="stylesheet" type="text/css" href="css/team.css">
<script src="https://d3js.org/d3.v3.min.js"></script>
<script src="https://d3js.org/d3.v4.min.js"></script>
<script src="js/team.js"></script>
<div class="select-style">
<select id="type" onchange="change(value)">
	<option value="W;勝">總勝場</option>
	<option value="L;敗">總敗場</option>
	<option value="HW;勝">主場勝場</option>
	<option value="HL;敗">主場敗場</option>
	<option value="AW;勝">客場勝場</option>
	<option value="AL;敗">客場敗場</option>
</select>
</div>
<svg width="960" height="500"></svg>