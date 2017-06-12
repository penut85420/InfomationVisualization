<!DOCTYPE html>
<html>
<head>
    <link rel="shortcut icon" type="image/png" href="img/icon.png"/>
    <link rel="shortcut icon" type="image/png" href="img/icon.png"/>
    <script type="text/javascript" src="//codeorigin.jquery.com/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="https://d3js.org/d3.v4.min.js"></script>
    <script type="text/javascript" src="main.js"></script>
    <title>逐場打擊率</title>
</head>
<body>
    <?php include("menu.php"); ?>
    <div>
    隊伍：
    <select id="team" onchange="changeTeam(value)">
        <option value="brother">中信兄弟</option>
        <option value="lamigo">Lamigo桃猿</option>
        <option value="fubon">富邦悍將</option>
        <option value="lion">7-11統一獅</option>
    </select>
    選手：
    <select id="member" onchange="change(value)">
    </select>
    場數：
    <select id="displayNum">
        <option value="all">本季</option>
        <option value="10">近10場</option>
        <option value="5">近5場</option>
    </select>
    </div>
<svg width="960" height="500"></svg>
</body>
</html>