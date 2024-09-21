package arraylab;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.util.ArrayList;
import java.util.Collections;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

public class FXMLDocumentController implements Initializable {

    @FXML
    private Label arrayListLabel;

    @FXML
    private TextField numberInputTextField;

    @FXML
    private TextField positionInputTextField;

    @FXML
    private Button addSingleButton;

    @FXML
    private Button addGroupButton;

    @FXML
    private Button deletePositionButton;

    @FXML
    private Label arrayListSortedLabel;

    @FXML
    private Label arraySizeLabel;

    @FXML
    private Label errorLabel;

    private ArrayList<Integer> myList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        myList = new ArrayList<>();
        populateListWithRandomNumbers(20);
        displayListContents();
        System.out.println("Initial List: " + myList);
    }

    private void populateListWithRandomNumbers(int size) {
        for (int i = 0; i < size; i++) {
            myList.add((int) (Math.random() * 100));
        }
    }

    public void displayListContents() {
        arrayListLabel.setText("Current Array: " + myList.toString());
        arraySizeLabel.setText("Array size: " + String.valueOf(getSize()));
        errorLabel.setText("");
    }

    @FXML
    private void handleAddNumbers(ActionEvent event) {
        String numberInput = numberInputTextField.getText().trim();
        String positionInput = positionInputTextField.getText().trim();

        if (numberInput.isEmpty() || positionInput.isEmpty()) {
            errorLabel.setText("Please enter both numbers and position.");
            return;
        }

        try {
            int position = Integer.parseInt(positionInput);
            String[] numberStrings = numberInput.split("[,\\s]+");
            ArrayList<Integer> numbersToAdd = new ArrayList<>();
            
            for (String numStr : numberStrings) {
                numbersToAdd.add(Integer.parseInt(numStr));
            }

            addGroupOfNumbersAtPosition(numbersToAdd, position);
            displayListContents();
        } catch (NumberFormatException e) {
            errorLabel.setText("Invalid input. Please enter valid numbers and position.");
        }
    }

    public void addGroupOfNumbersAtPosition(ArrayList<Integer> numbers, int position) {
        if (position >= 0 && position <= myList.size()) {
            myList.addAll(position, numbers);
            displayListContents();
        } else {
            errorLabel.setText("Position out of bounds.");
        }
    }

    @FXML
    private void handleDeleteNumber(ActionEvent event) {
        String positionInput = positionInputTextField.getText().trim();

        if (positionInput.isEmpty()) {
            errorLabel.setText("Please enter a position.");
            return;
        }

        try {
            int position = Integer.parseInt(positionInput);
            deleteNumberAtPosition(position);
        } catch (NumberFormatException e) {
            errorLabel.setText("Invalid position. Please enter a valid integer.");
        }
    }

    public void deleteNumberAtPosition(int position) {
        if (position >= 0 && position < myList.size()) {
            myList.remove(position);
            displayListContents();
        } else {
            errorLabel.setText("Position out of bounds.");
        }
    }

    @FXML
    private void sortArrayList(ActionEvent event) {
        Collections.sort(myList);
        arrayListSortedLabel.setText("Sorted array: " + myList.toString());
        errorLabel.setText("");
    }

    @FXML
    private void highlightNumber(ActionEvent event) {
        String numberInput = numberInputTextField.getText().trim();

        if (numberInput.isEmpty()) {
            errorLabel.setText("Please enter a number to search.");
            return;
        }

        try {
            int number = Integer.parseInt(numberInput);
            int frequency = getFrequencyOfNumber(number);
            if (frequency == 0) {
                errorLabel.setText("Number not found.");
            } else {
                highlightOccurrences(number);
            }
        } catch (NumberFormatException e) {
            errorLabel.setText("Invalid number. Please enter a valid integer.");
        }
    }

    public int getFrequencyOfNumber(int number) {
        return Collections.frequency(myList, number);
    }

    private void highlightOccurrences(int number) {
        StringBuilder highlightedList = new StringBuilder();
        for (int currentNumber : myList) {
            if (currentNumber == number) {
                highlightedList.append("[").append(currentNumber).append("] ");
            } else {
                highlightedList.append(currentNumber).append(" ");
            }
        }
        arrayListLabel.setText(highlightedList.toString().trim());
    }

    public int getSize() {
        return myList.size();
    }
}
