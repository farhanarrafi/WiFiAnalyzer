/*
 * WiFi Analyzer
 * Copyright (C) 2017  VREM Software Development <VREMSoftwareDevelopment@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.vrem.wifianalyzer.navigation;

import android.support.annotation.NonNull;
import android.view.MenuItem;

import com.vrem.wifianalyzer.MainActivity;
import com.vrem.wifianalyzer.R;
import com.vrem.wifianalyzer.navigation.items.NavigationItem;
import com.vrem.wifianalyzer.navigation.items.NavigationItemFactory;
import com.vrem.wifianalyzer.navigation.options.NavigationOption;
import com.vrem.wifianalyzer.navigation.options.NavigationOptionFactory;

import java.util.List;

public enum NavigationMenu {
    ACCESS_POINTS(R.drawable.ic_network_wifi_grey_500_48dp, R.string.action_access_points, NavigationItemFactory.ACCESS_POINTS, NavigationOptionFactory.ALL_ON),
    CHANNEL_RATING(R.drawable.ic_wifi_tethering_grey_500_48dp, R.string.action_channel_rating, NavigationItemFactory.CHANNEL_RATING, NavigationOptionFactory.ALL_ON),
    CHANNEL_GRAPH(R.drawable.ic_insert_chart_grey_500_48dp, R.string.action_channel_graph, NavigationItemFactory.CHANNEL_GRAPH, NavigationOptionFactory.ALL_ON),
    TIME_GRAPH(R.drawable.ic_show_chart_grey_500_48dp, R.string.action_time_graph, NavigationItemFactory.TIME_GRAPH, NavigationOptionFactory.ALL_ON),
    EXPORT(R.drawable.ic_import_export_grey_500_48dp, R.string.action_export, NavigationItemFactory.EXPORT),
    CHANNEL_AVAILABLE(R.drawable.ic_location_on_grey_500_48dp, R.string.action_channel_available, NavigationItemFactory.CHANNEL_AVAILABLE),
    VENDOR_LIST(R.drawable.ic_list_grey_500_48dp, R.string.action_vendors, NavigationItemFactory.VENDOR_LIST),
    SETTINGS(R.drawable.ic_settings_grey_500_48dp, R.string.action_settings, NavigationItemFactory.SETTINGS),
    ABOUT(R.drawable.ic_info_outline_grey_500_48dp, R.string.action_about, NavigationItemFactory.ABOUT);

    private final int icon;
    private final int title;
    private final List<NavigationOption> navigationOptions;
    private final NavigationItem navigationItem;

    NavigationMenu(int icon, int title, @NonNull NavigationItem navigationItem, @NonNull List<NavigationOption> navigationOptions) {
        this.icon = icon;
        this.title = title;
        this.navigationItem = navigationItem;
        this.navigationOptions = navigationOptions;
    }

    NavigationMenu(int icon, int title, @NonNull NavigationItem navigationItem) {
        this(icon, title, navigationItem, NavigationOptionFactory.ALL_OFF);
    }

    public static NavigationMenu find(int index) {
        if (index < 0 || index >= values().length) {
            return ACCESS_POINTS;
        }
        return values()[index];
    }

    public int getTitle() {
        return title;
    }

    public void activateNavigationMenu(@NonNull MainActivity mainActivity, @NonNull MenuItem menuItem) {
        navigationItem.activate(mainActivity, menuItem, this);
    }

    public void activateOptions(@NonNull MainActivity mainActivity) {
        for (NavigationOption navigationOption : navigationOptions) {
            navigationOption.apply(mainActivity);
        }
    }

    public boolean isWiFiBandSwitchable() {
        return navigationOptions.contains(NavigationOptionFactory.WIFI_SWITCH_ON);
    }

    int getIcon() {
        return icon;
    }

    NavigationItem getNavigationItem() {
        return navigationItem;
    }

    List<NavigationOption> getNavigationOptions() {
        return navigationOptions;
    }
}
