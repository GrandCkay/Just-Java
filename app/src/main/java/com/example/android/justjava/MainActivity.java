/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/
package com.example.android.justjava;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    int pricePenCup = 5;
    int sugar = 1;
    int whippedCream = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("quantity", quantity);
    }

    @Override

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        quantity = savedInstanceState.getInt("quantity");
        displayQuantity(quantity);
    }


    /**
     * Кнопка +
     */
    public void increment(View view) {
        if (quantity < 10) {
            quantity++;
        } else {
            Toast.makeText(this, "Wow, STOP!", Toast.LENGTH_SHORT).show();
        }
        displayQuantity(quantity);
    }

    /**
     * Кнопка минус
     */
    public void decrement(View view) {
        if (quantity > 0) {
            quantity--;
        } else {
            Toast.makeText(this, "Click +", Toast.LENGTH_SHORT).show();
        }
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */

    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox sugarCheckBox = findViewById(R.id.sugar);
        boolean hasSugarCheckBox = sugarCheckBox.isChecked();

        EditText youNameEditText = findViewById(R.id.you_name);
        String youName = youNameEditText.getText().toString();

        int price = calculatePrice(hasWhippedCream, hasSugarCheckBox);

        createOrderSummary(youName, hasWhippedCream, hasSugarCheckBox, price);

        displayMessage(createOrderSummary(youName, hasWhippedCream, hasSugarCheckBox, price));

        Intent intentEmail = new Intent(Intent.ACTION_SEND);
        intentEmail.setType("*/*");
        intentEmail.putExtra(Intent.EXTRA_TEXT, createOrderSummary(youName, hasWhippedCream, hasSugarCheckBox, price));
        intentEmail.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.JustJava_for) + " " + youName + " " + getString(R.string.from_Serj));

            if (intentEmail.resolveActivity(getPackageManager()) != null) {
                startActivity(intentEmail);
            }
    }

    /**
     * Calculates the price of the order.
     * quantity is the number of cups of coffee ordered
     * pricePenCup is the  price one cup coffee
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasSugarCheckBox) {

        int totalPrice;

        if (hasWhippedCream) {
            totalPrice = (pricePenCup + whippedCream) * quantity;
        } else {
            totalPrice = pricePenCup * quantity;
        }
        if (hasSugarCheckBox) {
            totalPrice = totalPrice + (sugar * quantity);
        }
        return totalPrice;
    }

    /**
     * CreateOrderSummery the price of the order.
     * price is the calculatePrice
     */
    private String createOrderSummary(String youName, boolean hasWhippedCream, boolean hasSugarCheckBox, int price) {
        String messageTotalPrice = " " + getString(R.string.you_name, youName);
        messageTotalPrice += "\n " + getString(R.string.Добавить_взбитые_сливки) + " " + hasWhippedCream;
        messageTotalPrice += "\n " + getString(R.string.Добавить_сахар) + " " + hasSugarCheckBox;
        messageTotalPrice += "\n " + getString(R.string.Количество_чашек) + " " + quantity;
        messageTotalPrice += "\n " + getString(R.string.Цена) + " " + price + " $";
        messageTotalPrice += "\n " + getString(R.string.Cпасибо);
        return messageTotalPrice;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}