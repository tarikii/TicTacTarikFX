package com.example.tresenratlla;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.*;

public class GameController {

    private Statistics statistics = new Statistics();
    @FXML
    private Button acceptButton;
    @FXML
    private ImageView infoIcon;

    //Los botones de selección de modo
    @FXML
    private RadioButton computerComputer, humanHuman, humanComputer;

    //Todos los botones del GridPane
    @FXML
    private Button button1, button2, button3, button4, button5, button6, button7, button8, button9, ready;

    //Nos da información sobre como va la partida
    @FXML
    private Label informationText;

    //A que jugador le toca
    @FXML
    private int playerTurn = 0;

    //Un boolean que nos indica si es el turno de la IA o no
    @FXML
    private boolean turnIA = false;

    //El jugador que ha ganado la partida
    @FXML
    private String winnerPlayer;

    //El jugador que ha perdido la partida
    @FXML
    private String loserPlayer;

    //El panel que contiene los botones para jugar
    @FXML
    private GridPane panelButtons;

    //El panel que contiene las estadisticas de cada jugador
    @FXML
    private GridPane statsPanel;

    //Un arraylist de Button de todos los botones del juego que involucran jugar
    @FXML
    ArrayList<Button> buttonsGame;

    @FXML
    private boolean IAPlaying = false;

    //Indicamos si el jugador es solitario (juega contra la maquina)
    @FXML
    private boolean singlePlayer = false;

    @FXML
    private boolean gameEnd = false;

    //Cogemos la info jpg y la ponemos en la escena "About"
    Image info = new Image(getClass().getResourceAsStream("infoIcon.jpg"));

    //Un input dialog que nos deja escribir el texto que queramos
    TextInputDialog td = new TextInputDialog("enter any text");


    //El metodo que involucrara la app, si clickamos en ready, este metodo saltara
    @FXML
    protected void gaming(ActionEvent event) throws IOException {
        //Llenamos el arraylist con nuestros botones
        buttonsGame = new ArrayList<>(Arrays.asList(button1, button2, button3, button4, button5, button6, button7, button8, button9));
        //Si clickamos el boton seleccionable humano vs humano, saltara el metodo clickboxes, que recorrera
        //nuestro arraylist de botones
        if (humanHuman.isSelected()) {
            clearGame();
            buttonsGame.forEach(button -> {
                clickBoxes(button);
            });
            //Si se selecciona computer vs computer, saltara el metodo putTextButtonIAvIA
        } else if (computerComputer.isSelected()) {
            clearGame();
            putTextButtonIAvIA();
            //Si se selecciona humano vs computer, saltara el metodo putTextButtonIAvP
        } else if (humanComputer.isSelected()) {
            clearGame();
            putTextButtonIAvP();
        }
    }


    //En este metodo, llamaremos a los botones que pasamos con el metodo putTextButtonPVP, y por cada vez
    //que clicke el jugador un boton, dependiendo de a quien le toque saltara la X o la O
    @FXML
    protected void clickBoxes(Button button) {
        button.setOnMouseClicked(mouseEvent -> {
            putTextButtonPVP(button);
            button.setDisable(true);
            //Despues de hacer esto, miramos si ha terminado la partida o no
            try {
                gameOver();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    //En este metodo se centra el humano vs humano
    @FXML
    protected void putTextButtonPVP(Button button) {
        //Ponemos que la IAPlaying es falso y singlePlayer tambien, ya que estamos en multijugador
        IAPlaying = false;
        singlePlayer = false;
        //Si es el turno del jugador 0, a la que clicke el jugador, el boton aparecera con una X
        if (playerTurn % 2 == 0) {
            button.setText("X");
            //Actualizamos el label de abajo de Ready, que indica el estado del juego
            informationText.setText("Player O turn");
            //Ahora le toca al jugador 1
            playerTurn = 1;
        } else {
            //Si es el jugador 1, haremos lo mismo que arriba, simplemente poniendo una O en los botones que clicke
            button.setText("O");
            informationText.setText("Player X turn");
            //Despues de esto, le tocara al jugador 0, y asi hasta terminar
            playerTurn = 0;
        }
    }

    //Este metodo trata basicamente de poner las X y las O al azar
    @FXML
    protected void putTextButtonIAvIA() throws IOException {
        //La IA esta jugando, por lo tanto IAPlaying es true, singlePlayer es falso ya que no hay un jugador
        IAPlaying = true;
        singlePlayer = false;
        //Creamos una lista de nodos que rodee el panel de botones (GridPane) y pillamos sus children
        List<Node> list = panelButtons.getChildren();
        for (int i = 0; i < list.size(); i++) {
            //Cada vez que recorra, pillamos un button al azar, del boton 1 al 9
            Button button = (Button) list.get((int) ((Math.random() * 9)));

            //Si no hay texto en el boton, entramos en el if
            if (button.getText() == "") {
                //Aqui haremos lo mismo que en PVP, si le toca al jugador 0, dibujara una X
                if (playerTurn % 2 == 0) {
                    button.setText("X");
                    button.setDisable(true);
                    //Cambiamos a jugador 1
                    playerTurn = 1;
                } else {
                    //Si le toca al jugador 1, dibujara una O
                    button.setText("O");
                    button.setDisable(true);
                    playerTurn = 0;
                }
                //Si el boton ya tiene texto, restamos 1 al contador, para poder rellenar todos los botones
            } else {
                i--;
            }
        }
        //Luego de realizar el for por completo, miramos si hemos terminado o no
        gameOver();
    }


    //Metodo que pilla el array de botones y los borra
    protected void clearGame() {
        //Creamos una lista de nodos que rodee el panel de botones GridPane
        List<Node> list = panelButtons.getChildren();
        for (int i = 0; i < list.size(); i++) {
            //Dentro de este for, por cada posicion que pase, deja un texto vacio y vuelve a habiltar el boton
            Button button = (Button) list.get(i);
            button.setDisable(false);
            button.setText("");
        }
    }

    //Cerramos el Stage actual que tenemos si le damos al boton "acceptButton"
    @FXML
    protected void closeStage(ActionEvent event) {
        Stage stage = (Stage) acceptButton.getScene().getWindow();
        stage.close();
    }

    //Este metodo sacara una escena nueva, el About, que nos muestra quien es el creador de esta app.
    @FXML
    protected void informationGameScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TicTacToe.class.getResource("about.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage aboutStage = new Stage();
        aboutStage.setTitle("About");
        aboutStage.setScene(scene);
        aboutStage.initModality(Modality.NONE);
        aboutStage.show();
    }

    //Este metodo sacara una escena nueva, las Estadisticas, que nos muestran los jugadores, victorias, derrotas
    //y empates
    @FXML
    protected void statisticsGameScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TicTacToe.class.getResource("stats.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage statsStage = new Stage();
        statsStage.setTitle("Statistics of players");
        statsStage.setScene(scene);
        statsStage.initModality(Modality.NONE);
        statsStage.show();
    }

    //Este metodo lo sacaremos cuando el metodo gameOver(), no encuentre a ningun ganador
    @FXML
    protected void tieGameScene() throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ended in a tie!");
        alert.setHeaderText(null);
        alert.setContentText("No winner this time around!");
        alert.showAndWait();
    }

    //Un metodo donde simplemente cerramos el juego
    @FXML
    protected void closeGame() {
        System.exit(0);
    }

    //Metodo gameOver, donde muestra quien gana en cada modo de juego
    protected void gameOver() throws IOException {
        //Creamos la lista de nodos del GridPane, y pillamos su contenido
        List<Node> list = panelButtons.getChildren();
        //Un boolean end
        boolean end = false;
        //Un int de las veces jugadas
        int vecesJugadas = 0;
        for (int i = 0; i < list.size(); i++) {
            //Creamos un String llamado win, donde le indicaremos un switch
            String win = switch (i) {
                //En este switch, vigila todos los casos en los que la suma del texto de todos los botones
                //sea una linea perfecta, es decir XXX o OOO.
                case 0 -> button1.getText() + button2.getText() + button3.getText();
                case 1 -> button4.getText() + button5.getText() + button6.getText();
                case 2 -> button7.getText() + button8.getText() + button9.getText();
                case 3 -> button1.getText() + button5.getText() + button9.getText();
                case 4 -> button3.getText() + button5.getText() + button7.getText();
                case 5 -> button1.getText() + button4.getText() + button7.getText();
                case 6 -> button2.getText() + button5.getText() + button8.getText();
                case 7 -> button3.getText() + button6.getText() + button9.getText();
                default -> "";
            };

            //En caso de que win sea igual a XXX
            if (win.equals("XXX")) {
                //Actualizamos el estado del juego actual
                informationText.setText("X is the winner!");
                //El ganador pasa a ser Player X
                winnerPlayer = "Player X";
                //El ganador pasa a ser Player O
                loserPlayer = "Player O";
                //Si la IA no esta jugando, mostraremos la escena de PVP
                if (!IAPlaying) {
                    winnerScenePVP();
                } else {
                    //Sino, mostraremos la de la IA
                    winnerSceneIA();
                }
                //Como solo queremos mostrarlo una vez y que no recorra el for, hacemos un break
                break;
                //Si win es igual a OOO
            } else if (win.equals("OOO")) {
                //Actualizamos el estado del juego actual
                informationText.setText("O is the winner!");
                //El ganador pasa a ser Player O y el perdedor Player X
                winnerPlayer = "Player O";
                loserPlayer = "Player X";
                if (!IAPlaying) {
                    winnerScenePVP();
                } else {
                    winnerSceneIA();
                }
                break;
            }
            //Si todos los botones estan disabled, entramos en un switch de win
            else if(button1.isDisable() && button2.isDisable() && button3.isDisable() && button4.isDisable() && button5.isDisable() && button6.isDisable() && button7.isDisable() && button8.isDisable() && button9.isDisable()){
                switch (win){
                    //En caso de que no sea XXX o OOO, es decir, todos los anteriores, sumamos las veces jugadas
                    case "XXO", "XOX", "OXX", "OOX", "OXO", "XOO" -> {
                        vecesJugadas++;
                        //Si las veces jugadas son igual a 8, entramos en el if
                        if (vecesJugadas == 8) {
                            //Deshabilitamos todos los botones, gameEnd pasa a ser true, ya que acaba el juego
                            //Y mostramos la escena donde empatan los jugadores X y O
                            buttonsGame.forEach(this::buttonDisabled);
                            gameEnd = true;
                            tieGameScene();
                        }
                    }
                }
            }
        }
    }

    //Un metodo que cuando le llamas, pone el boton que le has pasado por parametro deshabilitado
    @FXML
    protected void buttonDisabled(Button button){
        button.setDisable(true);
    }

    //Un metodo que se llamara cuando elegimos el modo IA vs humano
    @FXML
    protected void putTextButtonIAvP() throws IOException {
        //La IA esta jugando y solo hay un jugador, por lo tanto estos dos booleans pasan a ser True
        IAPlaying = true;
        singlePlayer = true;
        //Creamos una lista de nodos del panel de botones GridPane, y pillamos su contenido
        List<Node> list = panelButtons.getChildren();
        for (int i = 0; i < list.size(); i++) {
            //Escogemos boton por boton
            Button button = (Button) list.get(i);

            //Si el boton esta vacio de texto, entramos al if
            if (button.getText() == "") {
                //Por cada boton, se espera que el jugador de click
                button.setOnMouseClicked(mouseEvent -> {
                    //Mientras no sea el turno de la IA, el jugador juega, rellenamos con X el jugador
                    while (!turnIA) {
                        button.setText("X");
                        button.setDisable(true);
                        //Despues de que haya jugado el humano, el turno de la IA pasa a true
                        turnIA = true;
                    }

                    //Si el turno de la IA es true, entramos al while
                    while (turnIA) {
                        //Hacemos un try del metodo computerMove, que le pasaremos botones del for
                        try {
                            computerMove(button);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        //Una vez hecho esto, el turno de la IA pasa a ser falso
                        turnIA = false;
                    }

                });
                //Si el boton del texto no esta vacio, restamos 1 al contador, para que recorra todos los botones
                //sin texto
            } else {
                i--;
            }
        }
        //Una vez hecho el for, miramos el estado del juego y indicamos el resultado
        gameOver();
    }

    //Una escena que sale cuando es modo PVP
    public void winnerScenePVP() {
        //Sacamos una ventana, donde nos dejara escribir, por defecto pondra "Player"
        TextInputDialog dialog = new TextInputDialog("Player");
        //El titulo de esta ventana
        dialog.setTitle("We have a winner!");
        //El titulo de la cabecera de esta ventana, indicando el ganador
        dialog.setHeaderText("Congratulations to " + winnerPlayer + " !");
        //Indicamos que por favor, ponga el nombre del jugador que ha ganado
        dialog.setContentText("Please enter the " + winnerPlayer + " name: ");

        //Hacemos lo mismo con el perdedor
        dialog.setContentText("Please enter the " + loserPlayer + " name: ");
    }


    //Una escena que aparece si esta la IA presente en el modo
    public void winnerSceneIA() {
        //Simplemente sacamos una alerta, indicando que ha ganado el Player X o O, depende del caso
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("We have a winner!");
        alert.setHeaderText("The winner is " + winnerPlayer + " !");
        alert.setContentText("Congratulations to " + winnerPlayer + " !");
        alert.showAndWait();
    }

    //Un metodo (sin uso) donde saltara si solo hay un jugador, indicaremos que ponga su nombre
    public void winnerSceneSinglePlayer() {
        TextInputDialog dialog = new TextInputDialog("Player");
        dialog.setTitle("We have a winner!");
        dialog.setHeaderText("Congratulations to " + winnerPlayer + " !");
        dialog.setContentText("Please enter the " + winnerPlayer + " name: ");
        Optional<String> winner = dialog.showAndWait();


    }

    //Un metodo que ayudara a los movimientos de la IA, enviandole por parametro un boton
    public void computerMove(Button button) throws IOException {
        //Un contador que indica el fin del juego, empezando por 0
        int finJuego = 0;
        //Una lista de nodos, donde le pasamos el contenido del GridPane panelButtons
        List<Node> listButtons = panelButtons.getChildren();
        for (int i = 0; i < 1; i++) {
            //Hacemos un for, donde por cada boton que escoja (Random) haga lo siguiente
            button = (Button) listButtons.get((int) ((Math.random() * 9)));

            //Si el boton tiene un texto vacio, entramos al if
            if (button.getText() == "") {
                //Como hemos decidido que el jugador humano sera la X, en este caso la maquina sera la O
                button.setText("O");
                //Deshabilitamos el boton
                button.setDisable(true);

                //Si el boton ya tiene texto, restamos y volvemos a buscar un boton random
            } else {
                i--;
            }
            //Una vez hecho esto, sumamos el contador de fin del juego
            finJuego++;

            //Si el contador llega a 9, decidiremos que el juego ha terminado y haremos un break del for para
            //que el programa no se quede bloqueado en el ultimo boton
            if (finJuego == 9) {
                break;
            }
        }
        //Una vez hecho este metodo, miramos como esta el estado del juego y que resultado queda
        gameOver();
    }
}