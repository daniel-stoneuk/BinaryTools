package com.danielstone.binarytools;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.ContextCompat;

import com.danielstone.materialaboutlibrary.ConvenienceBuilder;
import com.danielstone.materialaboutlibrary.MaterialAboutFragment;
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem;
import com.danielstone.materialaboutlibrary.items.MaterialAboutTitleItem;
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard;
import com.danielstone.materialaboutlibrary.model.MaterialAboutList;
import com.danielstone.materialaboutlibrary.util.OpenSourceLicense;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;

/**
 * Created by danielstone on 29/12/2017.
 */

public class AboutFragment extends MaterialAboutFragment {
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

        MaterialAboutCard androidIconicsLicenseCard = ConvenienceBuilder.createLicenseCard(getActivity(),
                new IconicsDrawable(getActivity())
                        .icon(CommunityMaterial.Icon.cmd_book)
                        .color(ContextCompat.getColor(getActivity(), R.color.mal_color_icon_light_theme))
                        .sizeDp(18),
                "Android Iconics", "2018", "Mike Penz",
                OpenSourceLicense.APACHE_2);

        return new MaterialAboutList(appCardBuilder.build(), authorCardBuilder.build(), supportDevelopment.build(), malLicenseCard, androidIconicsLicenseCard);
    }

    @Override
    protected int getTheme() {
        return R.style.AppTheme_MaterialAboutActivity_Fragment;
    }
}
