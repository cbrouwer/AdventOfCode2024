#!/bin/bash
# newday.sh - copy day_n template files to day_<number> equivalents

# Check if argument is provided
if [ -z "$1" ]; then
  echo "Usage: $0 <day number>"
  exit 1
fi

DAY_NUM=$1

# Copy template files
cp "day_n.kt" "day_${DAY_NUM}.kt"
cp "day_n_testinput.txt" "day_${DAY_NUM}_testinput.txt"
cp "day_n_input.txt" "day_${DAY_NUM}_input.txt"

echo "Created:"
echo " - day_${DAY_NUM}.kt"
echo " - day_${DAY_NUM}_testinput.txt"
echo " - day_${DAY_NUM}_input.txt"
