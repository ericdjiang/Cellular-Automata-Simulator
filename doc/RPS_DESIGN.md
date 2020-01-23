# kr211, kb325, edj9
# RPS Planning
We envision utilizing 2 classes, a Game class and a Rules class. Walking through the lifecycle of a game, a user would select a weapon they want to choose, and the input would be handled by the Game class. In the Rules class, there would be a hashmap instance variable where each key corresponds to a specific weapon String (e.g. "rock") and the values corresponds to a list of other weapons which the key beats. This hashmap would be generated via a textfile containing the weapon information in a predefined format. After the two user inputs are recieved and stored, the method getWinner() is called on the Rules class with the two inputs as parameters. Within the rules class, getWinner checks the instance variable hashmap containing rule definitions and returns the corresponding winner.

```
class Game
private Rule rules
private Player player1
private Player player2

//prompts users for weapon selection
public String getInput()
```

```
class Rule

private HashMap<String, List<String>> winMap;

public String getWinner(String Wep1, String Wep2)

public boolean isValid(String Wep)

public List<String> getWeapons()
```

```
abstract class Player
int score

public abstract String getInput(List<String> wepList)

public void win()

public void resetScore()
```