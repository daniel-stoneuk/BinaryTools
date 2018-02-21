package com.danielstone.binarytools;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;
import com.danielstone.materialaboutlibrary.ConvenienceBuilder;
import com.danielstone.materialaboutlibrary.MaterialAboutFragment;
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem;
import com.danielstone.materialaboutlibrary.items.MaterialAboutItemOnClickAction;
import com.danielstone.materialaboutlibrary.items.MaterialAboutTitleItem;
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard;
import com.danielstone.materialaboutlibrary.model.MaterialAboutList;
import com.danielstone.materialaboutlibrary.util.OpenSourceLicense;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;

/**
 * Created by danielstone on 29/12/2017.
 */

public class AboutFragment extends MaterialAboutFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    SharedPreferences preferences;

    @Override
    protected MaterialAboutList getMaterialAboutList(Context context) {
        MaterialAboutCard.Builder appCardBuilder = new MaterialAboutCard.Builder();

        // Add items to card

        appCardBuilder.addItem(new MaterialAboutTitleItem.Builder()
                .text("Binary Tools")
                .desc("© 2018 Daniel Stone")
                .icon(R.mipmap.ic_launcher)
                .build());

        appCardBuilder.addItem(ConvenienceBuilder.createVersionActionItem(getActivity(),
                new IconicsDrawable(getActivity())
                        .icon(CommunityMaterial.Icon.cmd_information_outline)
                        .color(ContextCompat.getColor(getActivity(), R.color.mal_color_icon_light_theme))
                        .sizeDp(18),
                "Version",
                false));


        MaterialAboutCard.Builder preferencesCardBuilder = new MaterialAboutCard.Builder();
        preferencesCardBuilder.title("Preferences");

        preferencesCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text("Decimal Places")
                .subText(String.valueOf(preferences.getInt(getString(R.string.pref_decimal_places), 20) + " (tap to change)"))
                .icon(new IconicsDrawable(getActivity())
                        .icon(CommunityMaterial.Icon.cmd_adjust)
                        .color(ContextCompat.getColor(getActivity(), R.color.mal_color_icon_light_theme))
                        .sizeDp(18))
                .setOnClickAction(new MaterialAboutItemOnClickAction() {
                                      @Override
                                      public void onClick() {
                                          new MaterialDialog.Builder(getActivity())
                                                  .title("Number of Decimal Places")
                                                  .content("Precision of decimals:")
                                                  .inputType(InputType.TYPE_CLASS_NUMBER)
                                                  .input("number of decimal places", String.valueOf(preferences.getInt(getString(R.string.pref_decimal_places), 20)), new MaterialDialog.InputCallback() {
                                                      @Override
                                                      public void onInput(MaterialDialog dialog, CharSequence input) {
                                                          preferences.edit().putInt(getString(R.string.pref_decimal_places), Integer.valueOf(String.valueOf(input))).apply();
                                                      }
                                                  })
                                                  .inputRange(1,2, Color.RED).show();
                                      }
                                  }
                )
                .build());

        MaterialAboutCard.Builder authorCardBuilder = new MaterialAboutCard.Builder();
        authorCardBuilder.title("Author");

        authorCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text("Daniel Stone")
                .subText("United Kingdom")
                .icon(new IconicsDrawable(getActivity())
                        .icon(CommunityMaterial.Icon.cmd_account)
                        .color(ContextCompat.getColor(getActivity(), R.color.mal_color_icon_light_theme))
                        .sizeDp(18))
                .build());

        authorCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text("Follow on GitHub")
                .icon(new IconicsDrawable(getActivity())
                        .icon(CommunityMaterial.Icon.cmd_github_circle)
                        .color(ContextCompat.getColor(getActivity(), R.color.mal_color_icon_light_theme))
                        .sizeDp(18))
                .setOnClickAction(ConvenienceBuilder.createWebsiteOnClickAction(getActivity(), Uri.parse("https://github.com/daniel-stoneuk")))
                .build());

        authorCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text("Follow on Twitter")
                .icon(new IconicsDrawable(getActivity())
                        .icon(CommunityMaterial.Icon.cmd_twitter)
                        .color(ContextCompat.getColor(getActivity(), R.color.mal_color_icon_light_theme))
                        .sizeDp(18))
                .setOnClickAction(ConvenienceBuilder.createWebsiteOnClickAction(getActivity(), Uri.parse("https://twitter.com/")))
                .build());

        MaterialAboutCard.Builder supportDevelopment = new MaterialAboutCard.Builder();

        supportDevelopment.title("Support Development");


        supportDevelopment.addItem(ConvenienceBuilder.createRateActionItem(getActivity(),
                new IconicsDrawable(getActivity())
                        .icon(CommunityMaterial.Icon.cmd_star)
                        .color(ContextCompat.getColor(getActivity(), R.color.mal_color_icon_light_theme))
                        .sizeDp(18),
                "Rate this app",
                null
        ));

        supportDevelopment.addItem(ConvenienceBuilder.createEmailItem(getActivity(),
                new IconicsDrawable(getActivity())
                        .icon(CommunityMaterial.Icon.cmd_email)
                        .color(ContextCompat.getColor(getActivity(), R.color.mal_color_icon_light_theme))
                        .sizeDp(18),
                "Send an email",
                true,
                "apps@daniel-stone.uk",
                "Question concerning Binary tools"));

        MaterialAboutCard malLicenseCard = ConvenienceBuilder.createLicenseCard(getActivity(),
                new IconicsDrawable(getActivity())
                        .icon(CommunityMaterial.Icon.cmd_book)
                        .color(ContextCompat.getColor(getActivity(), R.color.mal_color_icon_light_theme))
                        .sizeDp(18),
                "material-about-library", "2018", "Daniel Stone",
                OpenSourceLicense.APACHE_2);

        MaterialAboutCard materialDialogsLicenseCard = ConvenienceBuilder.createLicenseCard(getActivity(),
                new IconicsDrawable(getActivity())
                        .icon(CommunityMaterial.Icon.cmd_book)
                        .color(ContextCompat.getColor(getActivity(), R.color.mal_color_icon_light_theme))
                        .sizeDp(18),
                "material-dialogs", "2018", "afollestad",
                OpenSourceLicense.MIT);

        MaterialAboutCard androidIconicsLicenseCard = ConvenienceBuilder.createLicenseCard(getActivity(),
                new IconicsDrawable(getActivity())
                        .icon(CommunityMaterial.Icon.cmd_book)
                        .color(ContextCompat.getColor(getActivity(), R.color.mal_color_icon_light_theme))
                        .sizeDp(18),
                "Android Iconics", "2018", "Mike Penz",
                OpenSourceLicense.APACHE_2);

        return new MaterialAboutList(appCardBuilder.build(), preferencesCardBuilder.build(), authorCardBuilder.build(), supportDevelopment.build(), malLicenseCard, materialDialogsLicenseCard, androidIconicsLicenseCard);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        preferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        preferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected int getTheme() {
        return R.style.AppTheme_MaterialAboutActivity_Fragment;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_decimal_places))) {
            ((MaterialAboutActionItem) getList().getCards().get(1).getItems().get(0)).setSubText(String.valueOf(sharedPreferences.getInt(key, 20)));
            refreshMaterialAboutList();
        }
    }
}
