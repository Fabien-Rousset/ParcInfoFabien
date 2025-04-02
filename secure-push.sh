#!/bin/bash

# Ensure a commit message is provided
if [ -z "$1" ]; then
  echo "Error: Commit message required."
  echo "Usage: $0 \"Your commit message\""
  exit 1
fi

# Stash changes including untracked files
git stash -u

# Pull latest changes; exit on failure
if ! git pull; then
  echo "Error: git pull failed."
  git stash pop  # Restore stashed changes before exiting
  exit 1
fi

# Reapply stash and handle potential conflicts
if ! git stash pop; then
  echo "Conflict detected! Resolve conflicts manually, then:"
  echo "1. Add resolved files with 'git add .'"
  echo "2. Commit with 'git commit -m \"$1\"'"
  echo "3. Push with 'git push'"
  exit 1
fi

# Add all changes, commit, and push
git add .
git commit -m "$1"
git push