package principal;


import simple_soccer_lib.AbstractTeam;
import simple_soccer_lib.PlayerCommander;

public class ComandoTime extends AbstractTeam {

	public ComandoTime() {
		super("Sub7", 7, true);
	}

	@Override
	protected void launchPlayer(int ag, PlayerCommander comm) {
		System.out.println("Jogador Ativo");
		ComandoJogador p = new ComandoJogador(comm);
		p.start();
	}
}
