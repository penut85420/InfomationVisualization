<!DOCTYPE html>
<html>
<head>
    <?php include("head/head.php"); ?>
    <script type="text/javascript" src="js/era.js"></script>
<body>
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
    場數：
    <select id="displayNum">
        <option value="all">本季</option>
        <option value="10">最近十場</option>
        <option value="5">最近五場</option>
    </select>
    </div>
    <svg width="960" height="500"></svg>
</body>
</html>