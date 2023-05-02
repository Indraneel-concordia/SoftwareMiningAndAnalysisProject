package com.concordia.process.metrics;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.DepthWalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.diff.*;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;

public class CodeChurns {
	private static Git git;
	private static RevWalk revWalk;

	public static int getCodeChurn(Repository repository, RevCommit commit) throws IOException {
        int codeChurn = 0;

        try {
        	revWalk = new RevWalk(repository, 100);
            RevTree parentTree = commit.getParentCount() > 0 ? revWalk.parseTree(commit.getParent(0).getId()) : null;
            RevTree commitTree = revWalk.parseTree(commit.getId());

            if (parentTree != null) {
                try (ObjectReader reader = repository.newObjectReader();
                     DiffFormatter diffFormatter = new DiffFormatter(System.out)) {
                    diffFormatter.setRepository(repository);
                    List<DiffEntry> diffs = diffFormatter.scan(parentTree, commitTree);
                    for (DiffEntry diff : diffs) {
                        if (diff.getNewPath().endsWith(".java")) {
                            List<Edit> edits = diffFormatter.toFileHeader(diff).toEditList();
                            for (Edit edit : edits) {
                            	codeChurn += edit.getLengthA(); 
                                codeChurn += edit.getLengthB(); 
                            }
                        }
                    }
                }
            } else {
                
                codeChurn = countLines(commit.getFullMessage());
            }
        } catch (Exception e) {
			System.out.println("Error(s) occur");
			e.printStackTrace();
		} finally {
			git.close();
		}

        return codeChurn;
    }

    public static int countLines(String text) {
        String[] lines = text.split("\r\n|\r|\n");
        return lines.length;
    }
	  
	public static void main(String[] args) {
		try {
			String fileName = "/Users/kosur/OneDrive/Desktop/dataset/processMetrics.csv";
			FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write("CommitID,Code_Churn\n"); 
            
			FileRepositoryBuilder builder = new FileRepositoryBuilder();
		    Repository repo = builder.setGitDir(new File("/Users/kosur/OneDrive/Desktop/dataset/hadoop" + "/.git"))
		            .setMustExist(true).build();
		    String brandName = "remotes/origin/HADOOP-11671";
		    git = new Git(repo);
		    git.checkout().setName(brandName).call();

			Iterable<RevCommit> commits = git.log().all().call();
			int totalChurn = 0;
			int codeChurn = 0;
			int totalCommits = 0;
			StringBuilder row;
            for (RevCommit commit : commits) {
            	totalCommits ++;
            	System.out.println("Commit ID: " + commit.getId().getName());
                System.out.println("Date: " + commit.getAuthorIdent().getWhen());
                System.out.println("Message: " + commit.getFullMessage());
                
                codeChurn = getCodeChurn(repo, commit);
                
                row = new StringBuilder();
                fileWriter.write(row.append(commit.getId().getName()).append(",").append(codeChurn).append("\n").toString());
                System.out.println("Code churn: " + codeChurn);
                
                totalChurn += codeChurn;
            }
            fileWriter.close();
          
            System.out.println("Total of code churns in branch: " + totalChurn);
            System.out.println("Total of number of changes in branch: " + totalCommits);
		} catch (InvalidRemoteException e) {
			e.printStackTrace();
		} catch (TransportException e) {
			e.printStackTrace();
		} catch (GitAPIException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
