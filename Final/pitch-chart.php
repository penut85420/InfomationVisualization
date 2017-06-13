<!DOCTYPE html>
<?php include("head/head.php"); ?>
<script type="text/javascript" src="js/pitch.js"></script>
<link rel="stylesheet" type="text/css" href="css/hit.css">
<div class="select-style">
    隊伍：
    <select id="team" onchange="changeTeam(value, 'Pitcher')">
        <option value="brother">中信兄弟</option>
        <option value="lamigo">Lamigo桃猿</option>
        <option value="fubon">富邦悍將</option>
        <option value="lion">7-11統一獅</option>
    </select>
    選手：
    <select id="member" onchange="change(value)">
    </select>
    數據：
    <select id="type" onchange="changeType(value)">
        <option value="H">被安打</option>
        <option value="SO">三振</option>
        <option value="BB">保送</option>
        <option value="HR">被全壘打</option>
    </select>
    </div>
<svg width="960" height="500"></svg>