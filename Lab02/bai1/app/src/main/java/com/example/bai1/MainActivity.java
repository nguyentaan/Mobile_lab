package com.example.bai1;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonFile = findViewById(R.id.button_file);
        Button buttonEdit = findViewById(R.id.button_edit);

        registerForContextMenu(buttonFile);
        registerForContextMenu(buttonEdit);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.button_file) {
            getMenuInflater().inflate(R.menu.context_menu_file, menu);
        } else if (v.getId() == R.id.button_edit) {
            getMenuInflater().inflate(R.menu.context_menu_edit, menu);
        }
    }


    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        // Get the ID of the selected item
        int itemId = item.getItemId();

        // Check which item was selected using if-else statements
        if (itemId == R.id.menu_new) {
            showToast("New selected");
            return true;
        } else if (itemId == R.id.menu_save) {
            showToast("Save selected");
            return true;
        } else if (itemId == R.id.menu_load) {
            showToast("Load selected");
            return true;
        } else if (itemId == R.id.menu_exit) {
            showToast("Exit selected");
            return true;
        } else if (itemId == R.id.menu_cut) {
            showToast("Cut selected");
            return true;
        } else if (itemId == R.id.menu_copy) {
            showToast("Copy selected");
            return true;
        } else if (itemId == R.id.menu_paste) {
            showToast("Paste selected");
            return true;
        } else if (itemId == R.id.menu_delete) {
            showToast("Delete selected");
            return true;
        } else {
            // If the selected item is not handled, call the superclass method
            return super.onContextItemSelected(item);
        }
    }


    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void onFileButtonClick(View view) {
        openContextMenu(view);
    }

    public void onEditButtonClick(View view) {
        openContextMenu(view);
    }

    public void onViewButtonClick(View view) {
        // Handle View button click
    }

    public void onToolButtonClick(View view) {
        // Handle Tool button click
    }
}