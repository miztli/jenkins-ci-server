#!/bin/bash

set -e

# Download the release package generated by the Github when a tag of the release is created to storage account

: {AZURE_STORAGE_ACCOUNT:?"AZURE_STORAGE_ACCOUNT not set"}
: {AZURE_STORAGE_KEY:?"AZURE_STORAGE_KEY not set"}

CLI_VERSION=`cat $SYSTEM_ARTIFACTSDIRECTORY/metadata/version`
TAR_NAME=azure-cli-$CLI_VERSION.tar.gz

mkdir -p $BUILD_STAGINGDIRECTORY/github/ >/dev/null 2>&1
curl -sL https://github.com/Azure/azure-cli/archive/$TAR_NAME -o $BUILD_STAGINGDIRECTORY/github/$TAR_NAME

az storage blob upload -c releases -n $TAR_NAME -f $BUILD_STAGINGDIRECTORY/github/$TAR_NAME

# Make sure the URL is publicly available
HOMEBREW_UPSTREAM_URL=$(az storage blob url -c releases -n $TAR_NAME -otsv)
curl -sfS -I $HOMEBREW_UPSTREAM_URL >/dev/null
echo -n $HOMEBREW_UPSTREAM_URL > $BUILD_STAGINGDIRECTORY/github/upstream_url
