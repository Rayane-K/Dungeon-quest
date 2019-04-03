package Configuration;

public interface Configurable {
	public int getLargeur();

	public void setLargeur(int largeur);

	public int getHauteur();

	public void setHauteur(int hauteur);

	public int getNbPersonnage();

	public void setNbPersonnage(int nbPersonnage);

	public int getNbObstacle();

	public void setNbObstacle(int nbObstacle);

	public int getTEMPS_PAUSE();

	public void setTEMPS_PAUSE(int tEMPS_PAUSE);
	
	public void setDensitePoulet(int densitePoulet);
	
	public int getDensitePoulet();

}