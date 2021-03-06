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

package com.vrem.wifianalyzer.wifi.graph.channel;

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.TitleLineGraphSeries;
import com.vrem.wifianalyzer.wifi.band.WiFiChannel;
import com.vrem.wifianalyzer.wifi.band.WiFiChannels;
import com.vrem.wifianalyzer.wifi.graph.tools.GraphConstants;
import com.vrem.wifianalyzer.wifi.graph.tools.GraphViewWrapper;
import com.vrem.wifianalyzer.wifi.model.WiFiDetail;
import com.vrem.wifianalyzer.wifi.model.WiFiSignal;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

class DataManager implements GraphConstants {
    static int frequencyAdjustment(int frequency) {
        return frequency - (frequency % 5);
    }

    Set<WiFiDetail> getNewSeries(@NonNull List<WiFiDetail> wiFiDetails, @NonNull Pair<WiFiChannel, WiFiChannel> wiFiChannelPair) {
        Set<WiFiDetail> results = new TreeSet<>();
        for (WiFiDetail wiFiDetail : wiFiDetails) {
            if (isInRange(wiFiDetail.getWiFiSignal().getCenterFrequency(), wiFiChannelPair)) {
                results.add(wiFiDetail);
            }
        }
        return results;
    }

    DataPoint[] getDataPoints(@NonNull WiFiDetail wiFiDetail) {
        WiFiSignal wiFiSignal = wiFiDetail.getWiFiSignal();
        int frequency = frequencyAdjustment(wiFiSignal.getCenterFrequency());
        int frequencyStart = frequencyAdjustment(wiFiSignal.getFrequencyStart());
        int frequencyEnd = frequencyAdjustment(wiFiSignal.getFrequencyEnd());
        int level = wiFiSignal.getLevel();
        return new DataPoint[]{
            new DataPoint(frequencyStart, MIN_Y),
            new DataPoint(frequencyStart + WiFiChannels.FREQUENCY_SPREAD, level),
            new DataPoint(frequency, level),
            new DataPoint(frequencyEnd - WiFiChannels.FREQUENCY_SPREAD, level),
            new DataPoint(frequencyEnd, MIN_Y)
        };
    }

    void addSeriesData(@NonNull GraphViewWrapper graphViewWrapper, @NonNull Set<WiFiDetail> wiFiDetails) {
        for (WiFiDetail wiFiDetail : wiFiDetails) {
            DataPoint[] dataPoints = getDataPoints(wiFiDetail);
            if (graphViewWrapper.isNewSeries(wiFiDetail)) {
                graphViewWrapper.addSeries(wiFiDetail, new TitleLineGraphSeries<>(dataPoints), true);
            } else {
                graphViewWrapper.updateSeries(wiFiDetail, dataPoints, true);
            }
        }
    }

    private boolean isInRange(int frequency, Pair<WiFiChannel, WiFiChannel> wiFiChannelPair) {
        return frequency >= wiFiChannelPair.first.getFrequency() && frequency <= wiFiChannelPair.second.getFrequency();
    }

}
