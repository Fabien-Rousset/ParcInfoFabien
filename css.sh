#!/bin/bash

# Configuration
path="src/main/resources/static/css/"
less_file="${path}parcinfo.less"
css_file="${path}parcinfo.css"
min_css_file="${path}parcinfo.min.css"

# Check dependencies
check_dependency() {
    if ! command -v lessc &> /dev/null; then
        echo "Error: lessc is not installed. Please install with: npm install -g less"
        exit 1
    fi

    if ! lessc --help | grep -q -- --clean-css; then
        echo "Error: less-plugin-clean-css required. Install with: npm install -g less-plugin-clean-css"
        exit 1
    fi
}

# Check file existence
check_source_file() {
    if [ ! -f "$less_file" ]; then
        echo "Error: Source file $less_file not found"
        exit 1
    fi
}

# Compile LESS files
compile_less() {
    echo "Compiling LESS files..."

    # Regular CSS
    if lessc "$less_file" "$css_file"; then
        echo "Created: $css_file"
    else
        echo "Failed to compile regular CSS"
        exit 1
    fi

    # Minified CSS
    if lessc --clean-css "$less_file" "$min_css_file"; then
        echo "Created: $min_css_file"
    else
        echo "Failed to compile minified CSS"
        exit 1
    fi
}

# Main execution
main() {
    check_dependency
    check_source_file
    compile_less
    echo "Compilation completed successfully"
}

# Run main function
main