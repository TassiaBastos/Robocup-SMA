package jogadores;

import java.util.ArrayList;

import simple_soccer_lib.PlayerCommander;
import simple_soccer_lib.perception.FieldPerception;
import simple_soccer_lib.perception.MatchPerception;
import simple_soccer_lib.perception.PlayerPerception;
import simple_soccer_lib.utils.EFieldSide;
import simple_soccer_lib.utils.Vector2D;

public class BaseJogador {
	
	protected MatchPerception matchPerc;
	public static boolean isBallPossession;
	public static int playerRecebendo = -1;
	protected PlayerCommander commander;
	public PlayerPerception selfPerc;
	protected FieldPerception fieldPerc;
	

	
	

	protected void turnToPoint(Vector2D point) {
		Vector2D newDirection = point.sub(selfPerc.getPosition());
		commander.doTurnToDirectionBlocking(newDirection);
	}
	
	protected boolean isAlignToPoint(Vector2D point, double margin) {
		double angle = point.sub(selfPerc.getPosition()).angleFrom(selfPerc.getDirection());
		return angle < margin && angle > margin * (-1);
	}

	protected void dash(Vector2D point, int speed) {
		if (selfPerc.getPosition().distanceTo(point) <= 1)
			return;
		if (!isAlignToPoint(point, 15))
			turnToPoint(point);
		commander.doDashBlocking(speed);
	}

	

	protected void kickToPoint(Vector2D point, double intensity) {
		Vector2D newDirection = point.sub(selfPerc.getPosition());
		double angle = newDirection.angleFrom(selfPerc.getDirection());
		if (angle > 90 || angle < -90) {
			commander.doTurnToDirectionBlocking(newDirection);
			angle = 0;
		}
		commander.doKickBlocking(intensity, angle);
	}
	
	protected void dinamica(Vector2D ballPos, EFieldSide side) {
		PlayerPerception JogadorMaisProximo = getClosestPlayerPoint(ballPos, side, 2, 0);
		PlayerPerception proximo = getClosestPlayerPoint(ballPos, side, 2, JogadorMaisProximo.getUniformNumber());
		
		if (proximo.getUniformNumber() == selfPerc.getUniformNumber())
			dash(ballPos, 70);
		
		if (JogadorMaisProximo.getUniformNumber() == selfPerc.getUniformNumber()) { 
			dash(ballPos, 70);
		
			if (isPointsAreClose(selfPerc.getPosition(), ballPos, 2)) {
				turnToPoint(proximo.getPosition());
				kickToPoint(proximo.getPosition(), 60);
				setPlayerRecebendo(proximo.getUniformNumber());
			}
		}
		
	}

	protected boolean isPointsAreClose(Vector2D reference, Vector2D point, double margin) {
		return reference.distanceTo(point) <= margin;
	}
	
	protected boolean isOffside(Vector2D playerReceive, ArrayList<PlayerPerception> lp, EFieldSide mySide) {

		for (PlayerPerception p : lp) {
			if (p.getPosition() == null)
				break;
			if (mySide.equals(EFieldSide.LEFT)) {
				if (playerReceive.getX() < p.getPosition().getX() && !p.isGoalie())
					return false;
			} else {
				if (playerReceive.getX() > p.getPosition().getX() && !p.isGoalie())
					return false;
			}
		}
		return true;
	}
	
	
	public void updatePerceptions() {
		PlayerPerception newSelf = commander.perceiveSelfBlocking();
		FieldPerception newField = commander.perceiveFieldBlocking();
		MatchPerception newMatch = commander.perceiveMatchBlocking();
		if (newSelf != null)
			this.selfPerc = newSelf;
		if (newField != null)
			this.fieldPerc = newField;
		if (newMatch != null)
			this.matchPerc = newMatch;
	}

	protected PlayerPerception getClosestPlayerPoint(Vector2D point, EFieldSide side, double margin, int ignoreUniform) {
		ArrayList<PlayerPerception> lp = fieldPerc.getTeamPlayers(side);
		PlayerPerception np = null;
		if (lp != null && !lp.isEmpty()) {
			double dist=0, temp;
			dist = lp.get(0).getPosition().distanceTo(point);
			np = lp.get(0);

			if (isPointsAreClose(np.getPosition(), point, margin))
				return np;
			for (PlayerPerception p : lp) {
				if (p.getUniformNumber() != ignoreUniform){
					if (p.getPosition() == null)
						break;
					if (isPointsAreClose(p.getPosition(), point, margin))
						return p;
					temp = p.getPosition().distanceTo(point);
					if (temp < dist) {
						dist = temp;
						np = p;
					}
				}
			}
		}
		return np;
	}
	
	public BaseJogador(PlayerCommander player) {
		commander = player;
	}

	public boolean isBallPossession() {
		return isBallPossession;
	}

	public void setBallPossession(boolean isBallPossession) {
		BaseJogador.isBallPossession = isBallPossession;
	}

	public static int getPlayerRecebendo() {
		return playerRecebendo;
	}

	public static void setPlayerRecebendo(int playerRecebendo) {
		BaseJogador.playerRecebendo = playerRecebendo;
	}


}
