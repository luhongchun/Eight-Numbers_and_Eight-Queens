import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


public class AStarSearch
{
	public static void search(int[] board, char heuristic)
	{
		Node root = new Node(new EightPuzzle(board));
		Queue<Node> q = new LinkedList<Node>();
		q.add(root);

		int searchCount = 1;
        int nodes = 0;
		while (!q.isEmpty())
		{
			Node tempNode = (Node) q.poll();

			if (!tempNode.getCurState().isGoal())
			{
				ArrayList<State> tempSuccessors = tempNode.getCurState()
						.getSuccessors();
                nodes+=tempSuccessors.size();
				ArrayList<Node> nodeSuccessors = new ArrayList<Node>();
				for (int i = 0; i < tempSuccessors.size(); i++)
				{
					Node checkedNode;
					if (heuristic == 's')
					{
						checkedNode = new Node(tempNode,
								tempSuccessors.get(i), tempNode.getCost()
										+ tempSuccessors.get(i).findCost(),
								((EightPuzzle) tempSuccessors.get(i))
										.getOutOfPlace());
					}
					else
					{
						checkedNode = new Node(tempNode,
								tempSuccessors.get(i), tempNode.getCost()
										+ tempSuccessors.get(i).findCost(),
								((EightPuzzle) tempSuccessors.get(i))
										.getManDist());
					}

					if (!checkRepeats(checkedNode))
					{
						nodeSuccessors.add(checkedNode);
					}
				}

				if (nodeSuccessors.size() == 0)
					continue;
				Node lowestNode = nodeSuccessors.get(0);

				for (int i = 0; i < nodeSuccessors.size(); i++)
				{
					if (lowestNode.getFCost() > nodeSuccessors.get(i)
							.getFCost())
					{
						lowestNode = nodeSuccessors.get(i);
					}
				}

				int lowestValue = (int) lowestNode.getFCost();
				for (int i = 0; i < nodeSuccessors.size(); i++)
				{
					if (nodeSuccessors.get(i).getFCost() == lowestValue)
					{
						q.add(nodeSuccessors.get(i));
					}
				}

				searchCount++;
			}
			else
			// The goal state has been found.
			{
				Stack<Node> solutionPath = new Stack<Node>();
				solutionPath.push(tempNode);
				tempNode = tempNode.getParent();

				while (tempNode.getParent() != null)
				{
					solutionPath.push(tempNode);
					tempNode = tempNode.getParent();
				}
				solutionPath.push(tempNode);

				int loopSize = solutionPath.size();
				for (int i = 0; i < loopSize; i++)
				{
					tempNode = solutionPath.pop();
					tempNode.getCurState().printState();
					System.out.println();
					System.out.println();
				}
				System.out.println("The total cost: " + tempNode.getCost());
				System.out.println("The total generated nodes:" + nodes);
				System.exit(0);
			}
		}

		System.out.println("Error! No solution found!");

	}

	private static boolean checkRepeats(Node n)
	{
		boolean retValue = false;
		Node checkNode = n;

		while (n.getParent() != null && !retValue)
		{
			if (n.getParent().getCurState().equals(checkNode.getCurState()))
			{
				retValue = true;
			}
			n = n.getParent();
		}

		return retValue;
	}

}
