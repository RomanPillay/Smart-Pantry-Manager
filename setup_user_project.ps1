$baseDir = "C:\Users\Roman\AndroidStudioProjects\SmartPantryManager2"
$packagePath = "com\example\smartpantrymanager"
$javaDir = "$baseDir\app\src\main\java\$packagePath"
$resDir = "$baseDir\app\src\main\res"

New-Item -ItemType Directory -Force -Path "$javaDir"
New-Item -ItemType Directory -Force -Path "$resDir\layout"
New-Item -ItemType Directory -Force -Path "$resDir\values"
New-Item -ItemType Directory -Force -Path "$resDir\menu"

# Remove default MainActivity
if (Test-Path "$javaDir\MainActivity.java") {
    Remove-Item "$javaDir\MainActivity.java" -Force
}
if (Test-Path "$resDir\layout\activity_main.xml") {
    Remove-Item "$resDir\layout\activity_main.xml" -Force
}

# Update AndroidManifest.xml
@"
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <application
        android:allowBackup="true"
        android:dataExtractionRules="@xml/data_extraction_rules"
        android:fullBackupContent="@xml/backup_rules"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.SmartPantryManager2"
        tools:targetApi="31">
        
        <activity
            android:name=".PantryListActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        
        <activity android:name=".AddEditIngredientActivity" />
        <activity android:name=".SuggestedRecipesActivity" />
        <activity android:name=".RecipeDetailActivity" />
        <activity android:name=".SettingsActivity" />
        
    </application>

</manifest>
"@ | Out-File -FilePath "$baseDir\app\src\main\AndroidManifest.xml" -Encoding utf8

# Menus
@"
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item
        android:id="@+id/nav_pantry"
        android:title="Pantry" />
    <item
        android:id="@+id/nav_recipes"
        android:title="Recipes" />
    <item
        android:id="@+id/nav_settings"
        android:title="Settings" />
</menu>
"@ | Out-File -FilePath "$resDir\menu\bottom_nav_menu.xml" -Encoding utf8

# Layouts
@"
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerViewPantry"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_above="@id/bottomNavigationView" />

    <com.google.android.material.floatingactionbutton.FloatingActionButton
        android:id="@+id/fabAddIngredient"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_above="@id/bottomNavigationView"
        android:layout_alignParentEnd="true"
        android:layout_margin="16dp"
        android:contentDescription="Add Ingredient" />

    <com.google.android.material.bottomnavigation.BottomNavigationView
        android:id="@+id/bottomNavigationView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:background="?android:attr/windowBackground"
        android:menu="@menu/bottom_nav_menu" />
</RelativeLayout>
"@ | Out-File -FilePath "$resDir\layout\activity_pantry_list.xml" -Encoding utf8

@"
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp">

    <EditText
        android:id="@+id/editTextName"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Ingredient Name" />

    <EditText
        android:id="@+id/editTextQuantity"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Quantity"
        android:inputType="numberDecimal" />

    <Spinner
        android:id="@+id/spinnerUnit"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="8dp"
        android:layout_marginBottom="8dp" />

    <EditText
        android:id="@+id/editTextExpiry"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Expiry Date"
        android:focusable="false"
        android:inputType="date" />

    <Button
        android:id="@+id/buttonSave"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Save Ingredient" />
</LinearLayout>
"@ | Out-File -FilePath "$resDir\layout\activity_add_edit_ingredient.xml" -Encoding utf8

@"
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerViewRecipes"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_above="@id/bottomNavigationView" />

    <TextView
        android:id="@+id/textViewEmpty"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        android:text="No recipes match your pantry yet - add more ingredients"
        android:visibility="gone" />

    <com.google.android.material.bottomnavigation.BottomNavigationView
        android:id="@+id/bottomNavigationView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:background="?android:attr/windowBackground"
        android:menu="@menu/bottom_nav_menu" />
</RelativeLayout>
"@ | Out-File -FilePath "$resDir\layout\activity_suggested_recipes.xml" -Encoding utf8

@"
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp">

    <TextView
        android:id="@+id/textViewTitle"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:textSize="24sp"
        android:textStyle="bold" />

    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Ingredients:"
        android:layout_marginTop="16dp"
        android:textStyle="bold" />

    <TextView
        android:id="@+id/textViewIngredients"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />

    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Instructions:"
        android:layout_marginTop="16dp"
        android:textStyle="bold" />

    <TextView
        android:id="@+id/textViewInstructions"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
</LinearLayout>
"@ | Out-File -FilePath "$resDir\layout\activity_recipe_detail.xml" -Encoding utf8

@"
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp">

    <Switch
        android:id="@+id/switchAlerts"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Expiring-soon alerts" />

    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="16dp"
        android:text="Preferred Unit System" />

    <Spinner
        android:id="@+id/spinnerUnitPreference"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
        
    <View
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1" />

    <com.google.android.material.bottomnavigation.BottomNavigationView
        android:id="@+id/bottomNavigationView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="?android:attr/windowBackground"
        android:menu="@menu/bottom_nav_menu" />
</LinearLayout>
"@ | Out-File -FilePath "$resDir\layout\activity_settings.xml" -Encoding utf8

@"
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal"
    android:padding="16dp">

    <TextView
        android:id="@+id/textViewItemName"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:textSize="18sp"
        android:textStyle="bold" />

    <TextView
        android:id="@+id/textViewItemQuantity"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginEnd="8dp" />

    <TextView
        android:id="@+id/textViewItemExpiry"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />
</LinearLayout>
"@ | Out-File -FilePath "$resDir\layout\item_pantry_ingredient.xml" -Encoding utf8

@"
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical"
    android:padding="16dp">

    <TextView
        android:id="@+id/textViewRecipeName"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:textSize="18sp"
        android:textStyle="bold" />

</LinearLayout>
"@ | Out-File -FilePath "$resDir\layout\item_recipe.xml" -Encoding utf8

# Java files
@"
package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class PantryListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry_list);
        
        findViewById(R.id.fabAddIngredient).setOnClickListener(v -> {
            startActivity(new Intent(PantryListActivity.this, AddEditIngredientActivity.class));
        });
        
        setupBottomNav();
    }
    
    private void setupBottomNav() {
        // Bottom Navigation setup here
    }
}
"@ | Out-File -FilePath "$javaDir\PantryListActivity.java" -Encoding utf8

@"
package com.example.smartpantrymanager;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class AddEditIngredientActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_ingredient);
    }
}
"@ | Out-File -FilePath "$javaDir\AddEditIngredientActivity.java" -Encoding utf8

@"
package com.example.smartpantrymanager;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class SuggestedRecipesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);
        
        setupBottomNav();
    }
    
    private void setupBottomNav() {
        // Bottom Navigation setup here
    }
}
"@ | Out-File -FilePath "$javaDir\SuggestedRecipesActivity.java" -Encoding utf8

@"
package com.example.smartpantrymanager;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
    }
}
"@ | Out-File -FilePath "$javaDir\RecipeDetailActivity.java" -Encoding utf8

@"
package com.example.smartpantrymanager;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        
        setupBottomNav();
    }
    
    private void setupBottomNav() {
        // Bottom Navigation setup here
    }
}
"@ | Out-File -FilePath "$javaDir\SettingsActivity.java" -Encoding utf8
