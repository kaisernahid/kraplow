package com.chriscarr.bang;

import java.util.List;

public class Gatling extends Card implements Playable {
	public Gatling(String name, int suit, int value, int type) {
		super(name, suit, value, type);
	}

	/* (non-Javadoc)
	 * @see com.chriscarr.bang.Playable#canPlay(com.chriscarr.bang.Player, java.util.List, int)
	 */
	public boolean canPlay(Player player, List<Player> players, int bangsPlayed){			
		return true;
	}
	
	/* (non-Javadoc)
	 * @see com.chriscarr.bang.Playable#targets(com.chriscarr.bang.Player, java.util.List)
	 */
	public List<Player> targets(Player player, List<Player> players){
		return Turn.others(player, players);
	}
	
	/* (non-Javadoc)
	 * @see com.chriscarr.bang.Playable#play(com.chriscarr.bang.Player, java.util.List, com.chriscarr.bang.UserInterface, com.chriscarr.bang.Deck, com.chriscarr.bang.Discard)
	 */
	public void play(Player currentPlayer, List<Player> players, UserInterface userInterface, Deck deck, Discard discard){
		discard.add(this);
		Player gatlingPlayer = Turn.getNextPlayer(currentPlayer, players);
		while(gatlingPlayer != currentPlayer){
			if (Turn.isBarrelSave(gatlingPlayer, deck, discard, userInterface) > 0){
				return;
			}
			int missPlayed = Turn.validPlayMiss(gatlingPlayer, userInterface);
			if(missPlayed == -1){
				Turn.damagePlayer(gatlingPlayer, players, currentPlayer, 1, currentPlayer, deck, discard, userInterface);
			} else {
				discard.add(gatlingPlayer.getHand().remove(missPlayed));
			}
			gatlingPlayer = Turn.getNextPlayer(gatlingPlayer, players);
		}
	}
}