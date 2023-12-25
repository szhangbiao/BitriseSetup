#!/bin/sh
# Git commit message valid script
echo "Pre-Executing commit msg: `cat $1`"
COMMIT_MSG=`cat $1 | egrep "^(feat|fix|docs|style|refactor|test|build|ci|release)(\(.+\))?:.{1,100}"`
if [ "$COMMIT_MSG" = "" ]; then
  echo "---------------------------------------------------------------"
    echo "Invalid commit message format, e.g. '<type>(<scope>):<subject>'"
    echo "type include feat|fix|docs|style|refactor|test|build|ci|release"
    echo "scope is your modify module or your scope"
    echo "---------------------------------------------------------------"
    exit 1
fi