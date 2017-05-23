package com.orton.birthdaycard;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import java.text.NumberFormat;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }
    int quantity=1;
    public void increment(View view)
    {
        if(quantity==100)
            return;
        else
        quantity++;
        displayQuantity(quantity);
    }
    public void decrement(View view)
    {
        if(quantity==1)
            return;
        else
            quantity--;
        displayQuantity(quantity);
    }
    private void displayQuantity(int qty)
    {
        TextView quantityTextView=(TextView) findViewById(R.id.initial_qty);
        quantityTextView.setText(String.valueOf(quantity));
        Log.v("MainActivity","the quantity is " + quantity);
    }
    int basePrice;
    private int calculatePrice(boolean hasWhippedCream,boolean hasChocolate)
    {
        basePrice = 5;
        if(hasWhippedCream==true)
            basePrice += 1;
        if(hasChocolate==true)
            basePrice +=2 ;
        return basePrice=basePrice*quantity;

    }
    String priceMessage="";
    public void submitOrder(View view)
    {
        EditText nameField = (EditText) findViewById(R.id.name_text_field);
        String name = nameField.getText().toString();

        CheckBox whippedCream  = (CheckBox) findViewById(R.id.whipped_cream);
        boolean hasWhippedCream = whippedCream.isChecked();
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate);
        boolean hasChocolate = chocolate.isChecked();

        int price=calculatePrice(hasWhippedCream,hasChocolate);
        String orderSummary=createOrderSummary(name,price,hasWhippedCream,hasChocolate);
        //displayMessage(orderSummary);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT,"Order For "+name);
        intent.putExtra(Intent.EXTRA_TEXT,""+orderSummary);
        if(intent.resolveActivity(getPackageManager())!=null)
        {
            startActivity(intent);
        }
    }
    private void displayMessage(String orderSummary)
    {
        TextView summary= (TextView) findViewById(R.id.order_summary_text_view);
        summary.setText(""+orderSummary);
    }
    private String createOrderSummary(String name, int price, boolean hasWhippedCream, boolean hasChocolate)
    {
        String priceMessage = "Name :" + name;
        if(hasWhippedCream)
        priceMessage += "\nWhipped Cream Added" ;
        if(hasChocolate)
            priceMessage += "\nChocolate Added" ;
        priceMessage+="\nQuantity : "+quantity;
        priceMessage+="\nTotal : $"+price;
        priceMessage+="Thank You!" ;
        return priceMessage;
    }

}
