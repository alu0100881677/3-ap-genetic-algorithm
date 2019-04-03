package HAIA.ap3;

import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.solution.PermutationSolution;
import org.uma.jmetal.solution.impl.DefaultIntegerSolution;
import org.uma.jmetal.util.JMetalException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;


@SuppressWarnings("serial")
public class problema3AP extends AbstractIntegerProblem {
  private int         dimProblem = 10 ;
  private double [][][] costMatrix ;
  private final int PENALIZACION = 1000;
  private final int MAX_COST = 550;

  
  public problema3AP(String distanceFile) throws IOException {
    //costMatrix = readProblem(distanceFile) ;
	  costMatrix = createRandomProblem();
	  setNumberOfVariables(dimProblem * 3 + (dimProblem - 1));
	  setNumberOfObjectives(1);
	  setName("3-AP");
	  
	  List<Integer> lowerLimit = new ArrayList<>(getNumberOfVariables());
	  List<Integer> upperLimit = new ArrayList<>(getNumberOfVariables()) ;
	  
	  int cont = 0;
	  for (int i = 0; i < getNumberOfVariables(); i++) {
		  if( cont == 3) {
			  cont = 0;
			  lowerLimit.add(-1);
			  upperLimit.add(-1);
		  }
		  else {
			  cont++;
			  lowerLimit.add(0);
	    	  upperLimit.add(dimProblem);
		  }			  
	  }

	  setLowerLimit(lowerLimit);
	  setUpperLimit(upperLimit);
  }


  private double [][][] readProblem(String file) throws IOException {
    double [][][] matrix = null;

    InputStream in = getClass().getResourceAsStream(file);
    InputStreamReader isr = new InputStreamReader(in);
    BufferedReader br = new BufferedReader(isr);

    StreamTokenizer token = new StreamTokenizer(br);
    
    return matrix;
  }

private double[][][] createRandomProblem(){
	  double [][][] matrix = new double[dimProblem][dimProblem][dimProblem];
	  
	  for(int i = 0; i < dimProblem ; i++) {
      	for(int j = 0; j < dimProblem; j++) {
      		for(int k = 0; k < dimProblem;k++) {
      			Random x = new Random();
      			matrix[i][j][k] = x.nextInt(MAX_COST);
      		}		
      	}
      }
	  return matrix;
  }

  @Override
  public void evaluate(IntegerSolution solution) {
	  double fitness1 = 0.0 ;
		// TODO Auto-generated method stub
	  Vector<Integer> pos1 = new Vector<Integer>(dimProblem);
	  Vector<Integer> pos2 = new Vector<Integer>(dimProblem);
	  Vector<Integer> pos3 = new Vector<Integer>(dimProblem);
		
	  int dim = solution.getNumberOfVariables();
	  int i = 0;
	  int index1,index2,index3;
	  while (i < dim) { 
		  index1 = solution.getVariableValue(i);
		  if(pos1.contains(index1))
			  fitness1 += PENALIZACION;
		  else
			  pos1.add(index1);
		  i++;
		  
		  index2 = solution.getVariableValue(i);
		  if(pos2.contains(index2))
			  fitness1 += PENALIZACION;
		  else 
			  pos2.add(index2);
		  i++;
		  
		  index3 = solution.getVariableValue(i);
		  if(pos3.contains(index3))
			  fitness1 += PENALIZACION;
		  else 
			  pos3.add(index3);
		  i++;
		  
		  if(i != dim) {
			  if(solution.getVariableValue(i) != -1) {
				  //AQUI NO DEBERIA DE ENTRAR NUNCA
				  System.out.println("Este mensaje nunca deberia de mostrarse");
				  fitness1 += 5 * PENALIZACION;
			  }
			  i++;
		  }
		  
		  fitness1 += costMatrix[index1][index2][index3];
		  solution.setObjective(0, fitness1);
		  
		  
	  }
	  
	  
  }
  
  @Override
  public IntegerSolution createSolution() {
	  IntegerSolution sol = new DefaultIntegerSolution(this);
	  ArrayList<Integer> index1 = new ArrayList<Integer>(dimProblem);
	  ArrayList<Integer> index2 = new ArrayList<Integer>(dimProblem);
	  ArrayList<Integer> index3 = new ArrayList<Integer>(dimProblem);
	  for(int i = 0; i < dimProblem; i++) {
		  index1.add(i);
		  index2.add(i);
		  index3.add(i);
	  }
	  int i = 0;
	  while(i < sol.getNumberOfVariables()) {
		  Random r = new Random();
		  int val = r.nextInt(index1.size());
		  sol.setVariableValue(i, index1.get(val));
		  index1.remove(val);
		  i++;
		  
		  val = r.nextInt(index2.size());
		  sol.setVariableValue(i, index2.get(val));
		  index2.remove(val);
		  i++;
		  
		  val = r.nextInt(index3.size());
		  sol.setVariableValue(i, index3.get(val));
		  index3.remove(val);
		  i++;
		  if(i!= sol.getNumberOfVariables())
			  sol.setVariableValue(i, -1);
		  i++;
	  }
	  
    return sol ;
  }
  
}