import argparse
import json

parser = argparse.ArgumentParser(description='python Implementation')
parser.add_argument('--input_dir', type = str, default =None, help='input_dir')
parser.add_argument('--output_dir', default=None, help='output_dir')
parser.add_argument('--log_file', type=str, default=None, help='log_file')
parser.add_argument('--sim_threshold', type=float, default=0.4 , help='Similarity threshold')
parser.add_argument('--depth', type=int, default=4, help='Depth of all leaf nodes')
parser.add_argument('--sim_method', type=str, default='seqDistOnlyToken', help='similarity methods: seqDist, seqDistOnlyToken, seqDistVarCont)')
parser.add_argument('--alpha', type=float, default=0.9, help='seqDistVarCont <*> weights')
parser.add_argument('--is_training', action='store_true', help='is_training' )
parser.add_argument('--year', type = str, default='2019', help='current year')
parser.add_argument('--data_type', type = str, default=None, help='data type of os')
parser.add_argument('--garbage', type =json.loads, help='preprocessing as <token>')
parser.add_argument('--regex', nargs='*', help='preprocessing as <*>' )
parser.add_argument('--log_format', type = str, default=None, help='column structure')


args = parser.parse_args()

print('is_training:',args.is_training)
print('log_format:',args.log_format=='')
print('regex:',args.regex)
print('garbage:',args.garbage)
print('input:',args.input_dir)