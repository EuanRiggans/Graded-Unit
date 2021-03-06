package com.simplyrugby.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.simplyrugby.exceptions.PlayerNotFoundException;
import com.simplyrugby.exceptions.SkillCategoryNotFoundException;
import com.simplyrugby.objects.Player;
import com.simplyrugby.objects.SkillCategory;
import com.simplyrugby.objects.Squad;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Search utility class to search through objects for data
 *
 * @author Euan Riggans
 */
public final class Search {

    /**
     * Returns a Player object from a given player ID
     * <pre>
     *     {@code
     *      Player tempPlayer = Search.getPlayerFromID(102);
     *     }
     * </pre>
     *
     * @param playerID The ID of the player
     * @return An instance of the Player object from the ID
     * @throws PlayerNotFoundException Throws if there is no player matching the given ID, or if the player is not found
     */
    public static Player getPlayerFromID(int playerID) throws PlayerNotFoundException {
        ArrayList<Player> tempPlayers = new ArrayList<>();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        boolean found = false;
        Type playerType = new TypeToken<ArrayList<Player>>() {
        }.getType();
        try {
            Reader reader = new FileReader("data/players.json");
            tempPlayers = new Gson().fromJson(reader, playerType);
        } catch (FileNotFoundException e) {
            SimpleAlerts.exceptionAlert("No player data was found", e);
        }
        for (Player player : tempPlayers) {
            if (player.getUID() == playerID) {
                found = true;
                return player;
            }
        }
        if (!found) {
            throw new PlayerNotFoundException();
        }
        return null;
    }

    /**
     * Returns a SkillCategory object specified in the parameters
     *
     * @param skillCategoryName The name of the skill category
     * @param playerID          The id of the player who has the category
     * @return Returns the SkillCategory object for the specified parameters
     * @throws SkillCategoryNotFoundException Throws if the SkillCategory is not found in the players skills
     */
    public static SkillCategory getSkillCategoryFromName(String skillCategoryName, int playerID) throws SkillCategoryNotFoundException {
        ArrayList<Player> tempPlayers = new ArrayList<>();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        boolean found = false;
        Type playerType = new TypeToken<ArrayList<Player>>() {
        }.getType();
        try {
            Reader reader = new FileReader("data/players.json");
            tempPlayers = new Gson().fromJson(reader, playerType);
        } catch (FileNotFoundException e) {
            SimpleAlerts.exceptionAlert("No players data was found", e);
        }
        for (Player player : tempPlayers) {
            if (player.getUID() == playerID) {
                for (SkillCategory skillCategory : player.getSkills()) {
                    if (skillCategory.getCategory().equals(skillCategoryName)) {
                        found = true;
                        return skillCategory;
                    }
                }
            }
        }
        if (!found) {
            throw new SkillCategoryNotFoundException();
        }
        return null;
    }

    /**
     * Checks if a squad exists based on its name
     *
     * @param squadName The name of the squad being checked for
     * @return Returns true if the squad exists. Returns false if the squad does not exist.
     */
    public static boolean squadExists(String squadName) {
        int index = 0;
        int currentSquadIndex = 0;
        ArrayList<Squad> squads = new ArrayList<>();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        boolean found = false;
        Type playerType = new TypeToken<ArrayList<Player>>() {
        }.getType();
        try {
            Reader reader = new FileReader("data/squads.json");
            squads = new Gson().fromJson(reader, playerType);
        } catch (FileNotFoundException e) {
            SimpleAlerts.exceptionAlert("No squads data was found", e);
        }
        for (Squad squad : squads) {
            if (squad.getSquadName().toLowerCase().equals(squadName.toLowerCase())) {
                currentSquadIndex = index;
                return true;
            }
            index++;
        }
        return false;
    }
}
