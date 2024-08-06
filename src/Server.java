
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*package comp546pa1w2020;*/

/** Server class
 *
 * @author Kerly Titus
 */

public class Server extends Thread {
	
	/* NEW : Shared member variables are now static for the 2 receiving threads */
	private static int numberOfTransactions;         	/* Number of transactions handled by the server */
	private static int numberOfAccounts;             	/* Number of accounts stored in the server */
	private static int maxNbAccounts;                		/* maximum number of transactions */
	private static Accounts [] account;              		/* Accounts to be accessed or updated */
	/* NEW : member variabes to be used in PA2 with appropriate accessor and mutator methods */
	private String serverThreadId;				 /* Identification of the two server threads - Thread1, Thread2 */
	private static String serverThreadRunningStatus1;	 /* Running status of thread 1 - idle, running, terminated */
	private static String serverThreadRunningStatus2;	 /* Running status of thread 2 - idle, running, terminated */

    private static String serverThreadRunningStatus3;

    /** 
     * Constructor method of Client class
     * 
     * @return 
     * @param stid
     */
    Server(String stid) //don't forget!!!!!!!!!!!!!!!!!!!!!
    {
    	if ( !(Network.getServerConnectionStatus().equals("connected")))
    	{
    		System.out.println("\n Initializing the server ...");
    		numberOfTransactions = 0;
    		numberOfAccounts = 0;
    		maxNbAccounts = 100;
    		serverThreadId = stid;							/* unshared variable so each thread has its own copy */
    		serverThreadRunningStatus1 = "idle";				
    		account = new Accounts[maxNbAccounts];
    		System.out.println("\n Inializing the Accounts database ...");
    		initializeAccounts( );
    		System.out.println("\n Connecting server to network ...");
    		if (!(Network.connect(Network.getServerIP())))
    		{
    			System.out.println("\n Terminating server application, network unavailable");
    			System.exit(0);
    		}
    	} else if ((Network.getServerConnectionStatus().equals("connected")) && serverThreadRunningStatus2 == null) {
            serverThreadId = stid;							/* unshared variable so each thread has its own copy */
            serverThreadRunningStatus2 = "idle";
        } else if ((Network.getServerConnectionStatus().equals("connected")) && serverThreadRunningStatus3 == null){
            serverThreadId = stid;							/* unshared variable so each thread has its own copy */
            serverThreadRunningStatus3 = "idle";

        }

//        if ((Network.getServerConnectionStatus().equals("connected")) && serverThreadRunningStatus2 == null) {
//            serverThreadId = stid;							/* unshared variable so each thread has its own copy */
//            serverThreadRunningStatus2 = "idle";
//
//        }
//        if ((Network.getServerConnectionStatus().equals("connected")) && serverThreadRunningStatus3 == null){
//            serverThreadId = stid;							/* unshared variable so each thread has its own copy */
//            serverThreadRunningStatus3 = "idle";
//
//        }
    }
  
    /** 
     * Accessor method of Server class
     * 
     * @return numberOfTransactions
     * @param
     */
     public int getNumberOfTransactions()
     {
         return numberOfTransactions;
     }
         
    /** 
     * Mutator method of Server class
     * 
     * @return 
     * @param nbOfTrans
     */
     public void setNumberOfTransactions(int nbOfTrans)
     { 
         numberOfTransactions = nbOfTrans;
     }

    /** 
     * Accessor method of Server class
     * 
     * @return numberOfAccounts
     * @param
     */
     public int getNumberOfAccounts()
     {
         return numberOfAccounts;
     }
         
    /** 
     * Mutator method of Server class
     * 
     * @return 
     * @param nbOfAcc
     */
     public void setNumberOfAccounts(int nbOfAcc)
     { 
         numberOfAccounts = nbOfAcc;
     }
         
     /** 
      * Accessor method of Server class
      * 
      * @return maxNbAccounts
      * @param
      */
      public int getMxNbAccounts()
      {
          return maxNbAccounts;
      }
          
     /** 
      * Mutator method of Server class
      * 
      * @return 
      * @param nbOfAcc
      */
      public void setMaxNbAccounts(int nbOfAcc)
      { 
    	  maxNbAccounts = nbOfAcc;
      }
           
      /** 
       * Accessor method of Server class
       * 
       * @return serverThreadId
       * @param
       */
       public String getServerThreadId()
       {
           return serverThreadId;
       }
           
      /** 
       * Mutator method of Server class
       * 
       * @return 
       * @param tId
       */
       public void setServerThreadId(String stid)
       { 
     	  serverThreadId = stid;
       }

       /** 
        * Accessor method of Server class
        * 
        * @return serverThreadRunningStatus1
        * @param
        */
        public String getServerThreadRunningStatus1()
        {
            return serverThreadRunningStatus1;
        }
            
       /** 
        * Mutator method of Server class
        * 
        * @return 
        * @param runningStatus
        */
        public void setServerThreadRunningStatus1(String runningStatus)
        { 
      	  serverThreadRunningStatus1 = runningStatus;
        }
        
        /** 
         * Accessor method of Server class
         * 
         * @return serverThreadRunningStatus2
         * @param
         */
         public String getServerThreadRunningStatus2()
         {
             return serverThreadRunningStatus2;
         }

         public String getServerThreadRunningStatus3(){
             return serverThreadRunningStatus3;
         }

         public void setServerThreadRunningStatus3(String runningStatus){
             serverThreadRunningStatus3 = runningStatus;
         }

             
        /** 
         * Mutator method of Server class
         * 
         * @return 
         * @param runningStatus
         */
         public void setServerThreadRunningStatus2(String runningStatus)
         { 
       	  serverThreadRunningStatus2 = runningStatus;
         }
         
    /** 
     * Initialization of the accounts from an input file
     * 
     * @return 
     * @param
     */  
     public void initializeAccounts()
     {
        Scanner inputStream = null; /* accounts input file stream */
        int i = 0;                  /* index of accounts array */
        
        try
        {
         inputStream = new Scanner(new FileInputStream("src/account.txt"));
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File account.txt was not found");
            System.out.println("or could not be opened.");
            System.exit(0);
        }
        while (inputStream.hasNextLine())
        {
            try
            {   account[i] = new Accounts();
                account[i].setAccountNumber(inputStream.next());    /* Read account number */
                account[i].setAccountType(inputStream.next());      /* Read account type */
                account[i].setFirstName(inputStream.next());        /* Read first name */
                account[i].setLastName(inputStream.next());         /* Read last name */
                account[i].setBalance(inputStream.nextDouble());    /* Read account balance */                
            }
            catch(InputMismatchException e)
            {
                System.out.println("Line " + i + "file account.txt invalid input");
                System.exit(0);
            }
            i++;
        }
        setNumberOfAccounts(i);			/* Record the number of accounts processed */
        
//        System.out.println("\n DEBUG : Server.initializeAccounts() " + getNumberOfAccounts() + " accounts processed"); //uncommented
         System.out.print("");
        inputStream.close( );
     }
         
    /** 
     * Find and return the index position of an account 
     * 
     * @return account index position or -1
     * @param accNumber
     */
     public int findAccount(String accNumber)
     {
         int i = 0;
         
         /* Find account */
         while ( !(account[i].getAccountNumber().equals(accNumber)))
             i++;
         if (i == getNumberOfAccounts())
             return -1;
         else
             return i;
     }
     
    /** 
     * Processing of the transactions
     * 
     * @return 
     * @param trans
     */
     public boolean processTransactions(Transactions trans)
     {   int accIndex;             	/* Index position of account to update */
         double newBalance; 		/* Updated account balance */
         
         /* System.out.println("\n DEBUG : Server.processTransactions() " + getServerThreadId() ); */
         
         /* Process the accounts until the client disconnects */
         while ((!Network.getClientConnectionStatus().equals("disconnected")))
         {
             System.out.print(""); //WTFFFFFFFFFFFFFFFFF
//        	 while ( (Network.getInBufferStatus().equals("empty") && !Network.getClientConnectionStatus().equals("disconnected")) )
//        	 {
//        		 Thread.yield(); 	/* Yield the cpu if the network input buffer is empty */
//        	 } //uncommented
        	 
        	 if (!Network.getInBufferStatus().equals("empty"))
        	 {
                 System.out.print("");
//        		 System.out.println("\n DEBUG : Server.processTransactions() - transferring in account " + trans.getAccountNumber()); //uncommented

                 try {
                     Network.transferIn(trans);                              /* Transfer a transaction from the network input buffer */
                 } catch (InterruptedException e) {
                     throw new RuntimeException(e);
                 }

                 accIndex = findAccount(trans.getAccountNumber());
        		 /* Process deposit operation */
        		 if (trans.getOperationType().equals("DEPOSIT"))
        		 {
        			 newBalance = deposit(accIndex, trans.getTransactionAmount()); 
        			 trans.setTransactionBalance(newBalance);
        			 trans.setTransactionStatus("done");

                     System.out.print("");
//        			 System.out.println("\n DEBUG : Server.processTransactions() - Deposit of " + trans.getTransactionAmount() + " in account " + trans.getAccountNumber()); //uncommented
        		 }
        		 else
        			 /* Process withdraw operation */
        			 if (trans.getOperationType().equals("WITHDRAW"))
        			 {
        				 newBalance = withdraw(accIndex, trans.getTransactionAmount());
        				 trans.setTransactionBalance(newBalance);
        				 trans.setTransactionStatus("done");

                         System.out.print("");
//                         System.out.println("\n DEBUG : Server.processTransactions() - Withdrawal of " + trans.getTransactionAmount() + " from account " + trans.getAccountNumber()); //uncommented
        			 }
        			 else
        				 /* Process query operation */
        				 if (trans.getOperationType().equals("QUERY"))
        				 {
                            newBalance = query(accIndex);
                            trans.setTransactionBalance(newBalance);
                            trans.setTransactionStatus("done");

                             System.out.print("");
//                             System.out.println("\n DEBUG : Server.processTransactions() - Obtaining balance from account" + trans.getAccountNumber()); //uncommented
					} 

            	
//        		 while (Network.getOutBufferStatus().equals("full"))
//        		 {
//        			 Thread.yield();		/* Yield the cpu if the network output buffer is full */
//        		 }
                 System.out.print("");
        		 /* System.out.println("\n DEBUG : Server.processTransactions() - transferring out account " + trans.getAccountNumber()); */

                 try {
                     Network.transferOut(trans);                            		/* Transfer a completed transaction from the server to the network output buffer */
                 } catch (InterruptedException e) {
                     throw new RuntimeException(e);
                 }
                 setNumberOfTransactions( (getNumberOfTransactions() +  1) ); 	/* Count the number of transactions processed */
        	 }
         }
         System.out.print("");
         /* System.out.println("\n DEBUG : Server.processTransactions() - " + getNumberOfTransactions() + " accounts updated"); */
              
         return true;
     }
         
    /** 
     * Processing of a deposit operation in an account
     * 
     * @return balance
     * @param i, amount
     */
   
     public double deposit(int i, double amount)
     {  double curBalance;      /* Current account balance */

         synchronized (account[i])
         {
             curBalance = account[i].getBalance( );          /* Get current account balance */

             /* NEW : A server thread is blocked before updating the 10th , 20th, ... 70th account balance in order to simulate an inconsistency situation */
             if (((i + 1) % 10 ) == 0)
             {
                 try {
                     Thread.sleep(100);
                 }
                 catch (InterruptedException e) {

                 }
             }//idk if this if-statement should be sync

             System.out.print("");
//             System.out.println("\n DEBUG : Server.deposit - " + "i " + i + " Current balance " + curBalance + " Amount " + amount + " " + getServerThreadId());

             account[i].setBalance(curBalance + amount);     /* Deposit amount in the account */
             return account[i].getBalance ();                /* Return updated account balance */
             //synchonized as well

         }
         //synchronizing this block only because imagine client1 wants to deposit in account[0], you don't want client2 to also work on account[0]
         //however, client2 could do some work on account[1], so it is wiser to synchronize account [i]
         //in fact, if you sync the whole deposit method, and Thread1 is doing something you're blocking the deposit function to other to the other Threads
        

     }
         
    /**
     *  Processing of a withdrawal operation in an account
     * 
     * @return balance
     * @param i, amount
     */
 
     public double withdraw(int i, double amount)
     {
         synchronized (account[i]){
             double curBalance;      /* Current account balance */ //put it outside of sync block????
             curBalance = account[i].getBalance( );          /* Get current account balance */

             System.out.print("");
//             System.out.println("\n DEBUG : Server.withdraw - " + "i " + i + " Current balance " + curBalance + " Amount " + amount + " " + getServerThreadId());

             account[i].setBalance(curBalance - amount);     /* Withdraw amount in the account */

             return account[i].getBalance ();                /* Return updated account balance */

         }
         //dont sync the whole method of else no other threads can access this whole withdraw method, only sync account[i]

     }

    /**
     *  Processing of a query operation in an account
     * 
     * @return balance
     * @param i
     */
 
     public double query(int i)
     {
//         double curBalance;      /* Current account balance */

        //see slide 24 tut 2
         // sometimes it is more wasteful to synchronize the whole method when you only need to sync a small portion of the code

         synchronized (account[i]){
             double curBalance;      /* Current account balance */
             curBalance = account[i].getBalance( );          /* Get current account balance */

             System.out.print("");
//             System.out.println("\n DEBUG : Server.query - " + "i " + i + " Current balance " + curBalance + " " + getServerThreadId());

             return curBalance;
         }

//        return curBalance;                              /* Return current account balance */

         //sometimes it is more wasteful to synchronized the whole method when you only need to sync a small portion of the code
     }
         
     /**
      *  Create a String representation based on the Server Object
     * 
     * @return String representation
     */
     public String toString() 
     {	
    	 return ("\n server IP " + Network.getServerIP() + "connection status " + Network.getServerConnectionStatus() + "Number of accounts " + getNumberOfAccounts());
     }
     
    /**
     * Code for the run method
     * 
     * @return 
     * @param
     */


    public void run()
    {
        //we have two servers --> break it into two like in client run()
        //disconnect when both threads have terminated

        Transactions trans = new Transactions();
        long serverStartTime1, serverEndTime1;
        long serverStartTime2, serverEndTime2;
        long serverStartTime3, serverEndTime3;

        if (getServerThreadId().equals("1"))
        {
            serverStartTime1 = System.currentTimeMillis();
            processTransactions(trans);
            serverEndTime1 = System.currentTimeMillis();
            System.out.println("\n Terminating server(1) thread - " + " Running time " + (serverEndTime1 - serverStartTime1) + " milliseconds");

            //set status to terminated
            setServerThreadRunningStatus1("Terminated"); //do i capitalize it?
        }
        if (getServerThreadId().equals("2")){
            serverStartTime2 = System.currentTimeMillis();
            processTransactions(trans);
            serverEndTime2 = System.currentTimeMillis();
            System.out.println("\n Terminating server(2) thread - " + " Running time " + (serverEndTime2 - serverStartTime2) + " milliseconds");

            //set status to terminated
            setServerThreadRunningStatus2("Terminated");

        }
        if (getServerThreadId().equals("3"))
        {
            serverStartTime3 = System.currentTimeMillis();
            processTransactions(trans);
            serverEndTime3 = System.currentTimeMillis();
            System.out.println("\n Terminating server(3) thread - " + " Running time " + (serverEndTime3 - serverStartTime3) + " milliseconds");

            //set status to terminated
            setServerThreadRunningStatus3("Terminated"); //do i capitalize it?
        }
//        if (getServerThreadId().equals("3"))
//        {
//            serverStartTime3 = System.currentTimeMillis();
//            processTransactions(trans);
//            serverEndTime3 = System.currentTimeMillis();
//            System.out.println("\n Terminating server(3) thread - " + " Running time " + (serverEndTime3 - serverStartTime3) + " milliseconds");
//
//            //set status to terminated
//            setServerThreadRunningStatus3("Terminated"); //do i capitalize it?
//        }
//        if (getServerThreadId().equals("2")){
//            serverStartTime2 = System.currentTimeMillis();
//            processTransactions(trans);
//            serverEndTime2 = System.currentTimeMillis();
//            System.out.println("\n Terminating server(2) thread - " + " Running time " + (serverEndTime2 - serverStartTime2) + " milliseconds");
//
//            //set status to terminated
//            setServerThreadRunningStatus2("Terminated");
//
//        }



        /* .....................................................................................................................................................................................................*/
        //if thread1 is terminated and thread2 is terminated:
        if (getServerThreadRunningStatus1().equals("Terminated") && getServerThreadRunningStatus2().equals("Terminated") && getServerThreadRunningStatus3().equals("Terminated"))
        {
            Network.disconnect(Network.getServerIP());
        }


    }





}


